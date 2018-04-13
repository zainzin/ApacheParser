package com.ef;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    public static void main(String[] args) throws Exception {
        HashMap<String, String> arguments = ParserUtils.commandLineParser(args);


        if (!InputUtils.containsKey(arguments, Constants.startDateKey) || !InputUtils.containsKey(arguments, Constants.durationKey)
                || !InputUtils.containsKey(arguments, Constants.thresholdKey) || !InputUtils.containsKey(arguments, Constants.accesslogKey)) {
            throw new Exception(Constants.argsNotPassedErrorMessage);
        }

        if (!InputUtils.isValidDate(arguments.get(Constants.startDateKey)) || !InputUtils.isValid(arguments.get(Constants.durationKey))
                || !InputUtils.isValidInt(arguments.get(Constants.thresholdKey)) || !InputUtils.isValid(arguments.get(Constants.accesslogKey))) {
            throw new Exception(Constants.argsErrorMessage);
        }

        String startDate = InputUtils.getValue(arguments, Constants.startDateKey);
        String duration = InputUtils.getValue(arguments, Constants.durationKey);
        int threshold = Integer.parseInt(InputUtils.getValue(arguments, Constants.thresholdKey));
        String filePath = InputUtils.getValue(arguments, Constants.accesslogKey);

        int calenderField = DateUtils.convertDurationStringToInt(duration);

        SimpleDateFormat sdf = DateUtils.inputArgDateFormater();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(startDate));
        calendar.add(calenderField, Constants.amountTime);
        String endTIme = sdf.format(calendar.getTime());

        List<ParsedLog> linesList = ParserUtils.logFileReader(filePath);

        // Java part
        Map<String, Long> counting = linesList.stream()
                .filter(parsedLog -> {
                    try {
                        return parsedLog.getDate().after(sdf.parse(startDate))
                                && parsedLog.getDate().before(sdf.parse(endTIme));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.groupingBy(ParsedLog::getIp, Collectors.counting()));

        counting.forEach((ip, count) -> {
            if (count > threshold) {
                String comment = ip + " has " + threshold + " or more requests between " + startDate + " and " + endTIme;
                System.out.println(comment);
            }
        });

        // MySql part
        try (SqlConnect sqlConnect = SqlConnect.getDbConnection()) {
            sqlConnect.createTable();
            System.out.println("Inserting records into PARSED_LOGS table. This could take a while...");
            for (ParsedLog parsedLog : linesList) {
                PreparedStatement preparedStatement = sqlConnect.conn.prepareStatement(Constants.insertLogsQuery);
                preparedStatement.setObject(1, parsedLog.getDate());
                preparedStatement.setString(2, parsedLog.getIp());
                preparedStatement.setString(3, parsedLog.getMethod());
                preparedStatement.setString(4, parsedLog.getResponseCode());

                sqlConnect.insert(preparedStatement);
            }

            String selectQuery = "SELECT COUNT(*) AS count, ip FROM PARSED_LOGS WHERE date BETWEEN '" + startDate + "' AND '" + endTIme + "' GROUP BY ip HAVING count > " + threshold + ";";
            ResultSet resultSet = sqlConnect.query(selectQuery);
            while (resultSet.next()) {
                String comment = resultSet.getString("ip") + " has " + threshold + " or more requests between " + startDate + " and " + endTIme;
                System.out.println(comment);
            }
        }
    }
}
