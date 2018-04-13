package com.ef;

public class Constants {
    // keys
    protected static final String startDateKey = "startDate";
    protected static final String durationKey = "duration";
    protected static final String thresholdKey = "threshold";
    protected static final String accesslogKey = "accesslog";

    // error messages
    protected static String argsNotPassedErrorMessage = "Arguments must be passed: startDate, duration, threshold and accesslog";
    protected static final String argsErrorMessage = "startDate must be date, example: 2017-01-01.13:00:00, " +
            "duration must be a number, " +
            "threshold must be a number, and " +
            "accesslog must be a path to the log file.";

    protected static final int amountTime = 1;

    // MySql
    private static final String mySqlHost = "localhost";
    private static final String mySqlPort = "3306";
    private static final String mySqlUsername = "root";
    private static final String mySqlPassword = "secret";
    private static final String mySqlDatabase = "Logs";
    protected static final String mySqlUrl = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s",
            mySqlHost, mySqlPort, mySqlDatabase, mySqlUsername, mySqlPassword);

    // Queries
    public static final String createLogsTableQuery = "CREATE TABLE IF NOT EXISTS PARSED_LOGS (id INT PRIMARY KEY AUTO_INCREMENT, date DATETIME, ip VARCHAR(20), method VARCHAR(20), responseCode INT);";
    public static final String insertLogsQuery = "INSERT INTO PARSED_LOGS (date, ip, method, responseCode) VALUES (?,?,?,?);";

}
