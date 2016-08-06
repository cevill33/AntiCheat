package me.anticheat.log;

public enum LogType {

    CHAT("CHAT"),
    MSG("MSG"),
    BAN("BAN"),
    KICK("KICK"),
    JOIN("JOIN"),
    QUIT("QUIT"),
    HACKS("HACKS"),
    MUTE("MUTE"),
    KILL("KILL");


    private String name;

    private LogType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }








}

