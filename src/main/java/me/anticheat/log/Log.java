package me.anticheat.log;

import java.util.ArrayList;
import java.util.List;

public class Log {

    public static List<Log> logs = new ArrayList();
    public static List<Log> oldlogs = new ArrayList();
    private int hour;
    private int minute;
    private String sender;
    private String message;
    private LogType type;


    public Log(int hour, int minute, String sender, LogType type, String message) {
        this.message = message;
        this.hour = hour;
        this.minute = minute;
        this.sender = sender;
        this.type = type;
        logs.add(this);
    }




    public int getHour() {
        return hour;
    }
    public static List<Log> getLogs() {
        return logs;
    }
    public String getMessage() {
        return message;
    }
    public int getMinute() {
        return minute;
    }
    public String getSender() {
        return sender;
    }
    public LogType getType() {
        return type;
    }












}
