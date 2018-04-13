package com.ef;

import java.util.Date;

public class ParsedLog {
    private Date date;
    private String ip;
    private String method;
    private String responseCode;

    public ParsedLog(Date date, String ip, String method, String responseCode) {
        this.date = date;
        this.ip = ip;
        this.method = method;
        this.responseCode = responseCode;
    }

    public Date getDate() {
        return date;
    }

    public String getIp() {
        return ip;
    }

    public String getMethod() {
        return method;
    }

    public String getResponseCode() {
        return responseCode;
    }

    @Override
    public String toString() {
        return "ParsedLog{" +
                "date='" + date + '\'' +
                ", ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                ", responseCode='" + responseCode + '\'' +
                '}';
    }
}
