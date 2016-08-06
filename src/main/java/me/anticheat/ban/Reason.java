package me.anticheat.ban;

import org.bson.Document;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jakob on 21.03.2016.
 */
public enum Reason {

    HACKING,
    BELEIDIGUNG,
    WERBUNG,
    PROVOKATION,
    GRIEFING,
    NOTUTORUAL,
    ASK;


    Reason() {

    }

    public static BanStats getBanStat(UUID uuid, Reason reasontype, String reason, UUID banner) {
        final long now = System.currentTimeMillis();

        //Wenn ASK
        if(reasontype.equals(ASK)) {
            return new BanStats(ExclusionType.MUTE, reasontype, now + 1000 * 60 * 20, banner, reason);
        }

        //Wenn Hacking
        if(reasontype.equals(HACKING)) {

            long bantime = 0;
            bantime = bantime + now;

            Document document = BanManager.getBanFile(uuid);
            if(document != null) {

                int ammount = (Integer) document.get("banpoints");

                if(ammount == 0) bantime = bantime + 1000*60*24*5;
                if(ammount == 1) bantime = bantime + 1000*60*24*30*2;
                if(ammount == 2) bantime = bantime + 1000*60*24*30*3;
                if(ammount == 3) bantime = -1;


            } else {
                bantime = bantime + 1000*60*60*24*5;  //(5 Tage)
            }


            return new BanStats(ExclusionType.BAN, reasontype, bantime, banner, reason);
        }


        //Wenn Beleidigung
        if(reasontype.equals(BELEIDIGUNG)) {

            long bantime = 0;
            bantime = bantime + now + 1000*60*60*2;
            Document document = BanManager.getBanFile(uuid);
            if(document != null) {
                int ammount = (Integer) document.get("mutepoints");
                bantime = bantime + (ammount*1000*60*60*2);
            }
            return new BanStats(ExclusionType.MUTE, reasontype, bantime, banner, reason);
        }


        //Wenn Provokation
        if(reasontype.equals(PROVOKATION)) {

            long bantime = 0;
            bantime = bantime + now + 1000*60*60;
            Document document = BanManager.getBanFile(uuid);
            if(document != null) {
                int ammount = (Integer) document.get("mutepoints");
                bantime = bantime + (ammount*1000*60*60);
            }
            return new BanStats(ExclusionType.MUTE, reasontype, bantime, banner, reason);
        }

        //Wenn Provokation
        if(reasontype.equals(WERBUNG)) {
            long bantime = 0;
            bantime = now + 1000*60*60*24*30;
            return new BanStats(ExclusionType.MUTE, reasontype, bantime, banner, reason);
        }

        //Wenn Griefen
        if(reasontype.equals(GRIEFING)) {

            long bantime = 0;
            bantime = bantime + now;

            Document document = BanManager.getBanFile(uuid);
            if(document != null) {

                int ammount = (Integer) document.get("banpoints");

                if(ammount == 0) bantime = bantime + 1000*60*24*30;
                if(ammount == 1) bantime = bantime + 1000*60*24*30*2;
                if(ammount == 2) bantime = bantime + 1000*60*24*30*3;
                if(ammount == 3) bantime = -1;


            } else {
                bantime = now + 1000*60*60*24*30;  //(5 Tage)
            }


            return new BanStats(ExclusionType.BAN, reasontype, bantime, banner, reason);
        }

        return null;
    }

    public static Reason getByString(String r) {

        for (Reason all : Reason.values()) {
            if(all.toString().equalsIgnoreCase(r)) {
                return all;
            }
        }
        return null;
    }


}
