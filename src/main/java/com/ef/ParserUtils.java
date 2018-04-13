package com.ef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ParserUtils {

    public static HashMap<String, String> commandLineParser(String[] args) {
        HashMap<String, String> arguments = new HashMap<>();

        String key;
        String value;
        int index;

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                // this is a key that starts with a --
                key = args[i].substring(2);
            } else if (args[i].startsWith("-")) {
                // this is a key that start with a -
                key = args[i].substring(1);
            } else {
                arguments.put(args[i], null);
                continue;
            }
            index = key.indexOf('=');

            if (index == -1) {
                if ((i + 1) < args.length) {
                    if (args[i + 1].charAt(0) != '-') {
                        arguments.put(key, args[i + 1]);
                        i++;
                    } else {
                        arguments.put(args[i], null);
                    }
                } else {
                    arguments.put(args[i], null);
                }
            } else {
                value = key.substring(index + 1);
                key = key.substring(0, index);
                arguments.put(key, value);
            }
        }
        return arguments;
    }

    public static List<ParsedLog> logFileReader(String filePath) throws Exception {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new Exception(filePath.concat(" was not found at the given path"));
        }

        List<ParsedLog> linesList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            String[] splitLines = st.split("\\|");
            Date logDate = DateUtils.logDateFormater().parse(splitLines[0].split("\\.")[0]);
            ParsedLog log = new ParsedLog(logDate, splitLines[1], splitLines[2], splitLines[3]);
            linesList.add(log);
        }
        return linesList;
    }
}
