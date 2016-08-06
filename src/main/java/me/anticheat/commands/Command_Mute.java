package me.anticheat.commands;

import me.anticheat.Main;
import me.anticheat.ban.BanManager;
import me.anticheat.ban.BanStats;
import me.anticheat.ban.Reason;
import me.anticheat.chat.ChatReport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.IllegalFormatException;

/**
 * Created by Jakob on 27.03.2016.
 */
public class Command_Mute implements CommandExecutor{

    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {

        Player p = (Player) cs;

        if(!p.hasPermission("vetoxcheat.mute.simple")) {
            p.sendMessage(Main.prefix + "§cDu darfst diesen Befehl nicht nutzen.");
            return true;
        }

        //IDBELEIDIGUNG
        if(args[0].equals("idbeleidigung")) {
            if(args.length == 2) {

                for (ChatReport report : ChatReport.reports) {
                    if(report.getId().equals(args[1])) {

                        System.out.println(report.getVictim());
                        System.out.println(report.getVictimuuid());


                        BanStats stats = Reason.getBanStat(report.getVictimuuid(), Reason.BELEIDIGUNG, "", p.getUniqueId());
                        BanManager.ban(report.getVictimuuid(), stats);
                        p.sendMessage(Main.prefix + "§4Der Spieler §f" + report.getVictim() + " §4wurde da er beleidigt hat gemutet.");
                        ChatReport.reports.remove(report);
                        return true;
                    }
                }
                p.sendMessage(Main.prefix + "§cDiese ReportID konnte nicht gefunden werden!");

            }
            return true;
        }

        //ID PROVOKATION
        if(args[0].equals("idprovokation")) {
            if(args.length == 2) {

                for (ChatReport report : ChatReport.reports) {
                    if(report.getId().equals(args[1])) {
                        BanManager.ban(report.getVictimuuid(), Reason.getBanStat(report.getVictimuuid(), Reason.PROVOKATION, "", p.getUniqueId()));
                        p.sendMessage(Main.prefix + "§4Der Spieler §f" + report.getVictim() + " §4wurde da er provokant war gemutet.");
                        ChatReport.reports.remove(report);
                        return true;
                    }
                }
                p.sendMessage(Main.prefix + "§cDiese ReportID konnte nicht gefunden werden!");

            }
            return true;
        }



        //ID WERBUNG
        if(args[0].equals("idwerbung")) {
            if(args.length == 2) {

                for (ChatReport report : ChatReport.reports) {
                    if(report.getId().equals(args[1])) {
                        BanManager.ban(report.getVictimuuid(), Reason.getBanStat(report.getVictimuuid(), Reason.WERBUNG, "", p.getUniqueId()));
                        p.sendMessage(Main.prefix + "§4Der Spieler §f" + report.getVictim() + " §4wurde da er geworben hat gemutet.");
                        ChatReport.reports.remove(report);
                        return true;
                    }
                }
                p.sendMessage(Main.prefix + "§cDiese ReportID konnte nicht gefunden werden!");

            }
            return true;
        }


        //ID NOTBAN
        if(args[0].equals("idno")) {
            if(args.length == 2) {

                for (ChatReport report : ChatReport.reports) {
                    if(report.getId().equals(args[1])) {
                        p.sendMessage(Main.prefix + "§4Der Report von §f" + report.getVictim() + " §4wurde aufgelöst.");
                        ChatReport.reports.remove(report);
                        return true;
                    }
                }
                p.sendMessage(Main.prefix + "§cDiese ReportID konnte nicht gefunden werden!");

            }
            return true;
        }




        return false;
    }
}
