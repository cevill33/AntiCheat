package me.anticheat.commands;

import me.anticheat.Main;
import me.anticheat.chat.ChatReportNormal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Jakob on 26.03.2016.
 */
public class Command_ChatReport implements CommandExecutor {


    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        Player p = (Player) cs;

        //Normale Variante:
        if(args.length == 1) {
            if(p.getLevel() < 3) {
                p.sendMessage(Main.prefix + "§cDu musst mind. Level 3 sein um den normalen Chatreport zu nutzen!");
                return true;
            }
            ChatReportNormal.report(p, args);

            return true;
        }

        //Advanced:
        if(args.length == 3) {
            if(p.getLevel() < 25) {
                p.sendMessage(Main.prefix + "§cDu musst mind. Level 25 sein um den professionellen Chatreport zu nutzen!");
                return true;
            }


            return true;
        }







        p.sendMessage(Main.prefix  + "§3Wie benutze ich den Chatreport?");
        if(p.getLevel() < 25) {
            p.sendMessage("  §7Mit den Chatreport kannst du Spieler melden,");
            p.sendMessage("  §7die sich im MC-Chat nicht nach den Regeln verhalten.");
            p.sendMessage("  §7Ab Level 3 kannst du mit §f/chatreport <Spieler> §7einen Spieler");
            p.sendMessage("  §7reporten. Dann bekommt ein Teammitglied den Report und kann");
            p.sendMessage("  §7ihn wenn nötig muten. Ab Level 25 kannst du");
            p.sendMessage("  §7übrigens den Spieler sofort mutes geben. Gib dann einfach,");
            p.sendMessage("  §7erneut /chatreport ein damit du es siehst wie es geht.");
            return true;
        }
        p.sendMessage("§7Mit den Chatreport kannst du Spieler melden die sich im Minecraft-Chat nicht ");
        p.sendMessage("§7nach den Regeln verhalten. Da du schob Level 25 bist kannst du den Profi");
        p.sendMessage("§7Chatreport nutzen. Dazu musst du nur dies eingeben: ");
        p.sendMessage(" §f/chatreport <Spieler> <Schimpfwort> <SchimpfwortGenauSoWieErEsGeschriebenHat>");
        p.sendMessage("§7Durch diesen Befehl kann unser System ihn manchmal automatisch muten ohne dass");
        p.sendMessage("§7ein Moderator kontaktiert werdem muss!");


        return false;
    }
}
