package me.anticheat.chat;

import me.anticheat.Main;
import me.anticheat.ban.BanManager;
import me.anticheat.ban.BanStats;
import me.anticheat.ban.Reason;
import me.anticheat.log.Log;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jakob on 26.03.2016.
 */
public class ChatReportNormal {

    public static ArrayList<String> swearwords = new ArrayList();
    public static ArrayList<String> cool = new ArrayList();

    static {
        swearwords.add("arschloch");
        swearwords.add("nazi");
        swearwords.add("hitler");
        swearwords.add("depp");
        swearwords.add("hure");
        swearwords.add("missgeburt");
        swearwords.add("behindert");
    }


    public static void report(Player p, String[] args) {
        Player target = Bukkit.getPlayerExact(args[0]);

        if(cool.contains(p.getName())) {
            p.sendMessage(Main.prefix + "§cDu kannst nur alle 10 Minuten jemanden reporten!");
            return;
        }


        if(args[0].equalsIgnoreCase(p.getName())) {
            p.sendMessage(Main.prefix + "§cDu kannst dich nich selbst reporten.");
           return;
        }

        if(target == null) {
            p.sendMessage(Main.prefix + "§cDieser Spieler ist nicht online!");
            return;
        }

        if(BanManager.isMuted(target.getUniqueId())) {
            p.sendMessage(Main.prefix + "§cDieser Spieler ist schon gemutet.");
            return;
        }

        cool.add(p.getName());
        final String n = p.getName();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.main, new Runnable() {
            public void run() {
                cool.remove(n);
            }
        },20*60*10);


        ArrayList<String> list = new ArrayList<String>();
        for(Log log : Log.logs) {
            if(log.getSender().equals(args[0])) {
                list.add(log.getMessage());
            }
        }

        if(list.isEmpty()) {
            p.sendMessage(Main.prefix + "§cDieser Spieler hat in den letzten 3 Minuten den Chat nicht benutzt.");
            return;
        }



        for(String all : list) {
            for (String word : swearwords) {
                if(all.contains(word)) {
                    p.sendMessage(Main.prefix + "§7Der Spieler §f" + args[0] + " §7wurde nun Reportet!");
                    BanStats stats = Reason.getBanStat(target.getUniqueId(), Reason.BELEIDIGUNG, word, null);
                    BanManager.ban(target.getUniqueId(), stats);
                    return;

                }
            }
        }

        p.sendMessage(Main.prefix + "§7Der Spieler §f" + args[0] + " §7wurde nun Reportet!");
        new ChatReport(p.getName(), target.getName(), target.getUniqueId(), "", list);




    }




}
