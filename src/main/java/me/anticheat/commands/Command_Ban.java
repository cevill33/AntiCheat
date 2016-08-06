package me.anticheat.commands;

import me.anticheat.Main;
import me.anticheat.api.UUIDConverter;
import me.anticheat.ban.BanManager;
import me.anticheat.ban.BanStats;
import me.anticheat.ban.ExclusionType;
import me.anticheat.ban.Reason;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Time;
import java.util.UUID;

public class Command_Ban implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {

        if(!cs.hasPermission("vetoxcheat.bancommand.simple")) {
            cs.sendMessage(Main.prefix + "§cDu darfst diesen Befehl nicht benutzen!");
            return true;
        }

        if(args.length == 2) {

            UUID uuid = UUID.fromString(UUIDConverter.getValue(args[0]));
            Reason reason = Reason.getByString(args[1]);



            //Check if reason is null
            if(reason == null) {
                cs.sendMessage(Main.prefix + "§c" + args[1] + " ist kein Grund, diese gibt es:");
                for (Reason all : Reason.values()) {
                    cs.sendMessage("  §e- §4" + all.toString());
                }
                return true;
            }

            UUID bannerid = null;
            if(cs instanceof Player) {
                bannerid = ((Player) cs).getUniqueId();
            }

            BanStats stats = Reason.getBanStat(uuid, reason, "", bannerid);
            if(stats.getExclusionType().equals(ExclusionType.BAN)) {
                //Check if is Banned:
                if(BanManager.isBanned(uuid)) {
                    cs.sendMessage(Main.prefix + "§cDer Spieler §f" + args[0] + "§c schon gebannt!");
                    return true;
                }
            }
            if(stats.getExclusionType().equals(ExclusionType.MUTE)) {
                //Check if is Banned:
                if(BanManager.isMuted(uuid)) {
                    cs.sendMessage(Main.prefix + "§cDer Spieler §f" + args[0] + "§c schon gemutet!");
                    return true;
                }
            }
            BanManager.ban(uuid, stats);

            cs.sendMessage(Main.prefix);
            if(stats.getExclusionType().equals(ExclusionType.BAN)) {
                cs.sendMessage("§4Gebannt: §f" + args[0]);
            }
            if(stats.getExclusionType().equals(ExclusionType.MUTE)) {
                cs.sendMessage("§4Gemutet: §f" + args[0]);
            }

            cs.sendMessage("§4Grund: " + "§f" + stats.getReasonType() + " (" + stats.getExtraReason() + " )");

            Time time = new Time(stats.getEnd());
            String end = time.toLocaleString();
            if(stats.getEnd() == -1) {
                end = "PERMANENT";
            }

            cs.sendMessage("§4EntbannungsDatum: §f" + end);





            return true;
        }




        /*

                Wenn noch ein Detail kommt:

         */
        if(args.length > 2) {

            UUID uuid = UUID.fromString(UUIDConverter.getValue(args[0]));
            Reason reason = Reason.getByString(args[1]);
            String extrareason = "";
            for(int i = 2; i < args.length; i++ ) {
                extrareason = extrareason + " " + args[i];
            }


            //Check if reason is null
            if(reason == null) {
                cs.sendMessage(Main.prefix + "§c" + args[1] + "ist kein Grund, diese gibts:");
                for (Reason all : Reason.values()) {
                    cs.sendMessage("  §e- §4" + all.toString());
                }
                return true;
            }

            UUID bannerid = null;
            if(cs instanceof Player) {
                bannerid = ((Player) cs).getUniqueId();
            }

            BanStats stats = Reason.getBanStat(uuid, reason, extrareason, bannerid);
            if(stats.getExclusionType().equals(ExclusionType.BAN)) {
                //Check if is Banned:
                if(BanManager.isBanned(uuid)) {
                    cs.sendMessage(Main.prefix + "§cDer Spieler §f" + args[0] + "§c schon gebannt!");
                    return true;
                }
            }
            if(stats.getExclusionType().equals(ExclusionType.MUTE)) {
                //Check if is Banned:
                if(BanManager.isMuted(uuid)) {
                    cs.sendMessage(Main.prefix + "§cDer Spieler §f" + args[0] + "§c schon gemutet!");
                    return true;
                }
            }
            BanManager.ban(uuid, stats);

            cs.sendMessage(Main.prefix);
            if(stats.getExclusionType().equals(ExclusionType.BAN)) {
                cs.sendMessage("§4Gebannt: §f" + args[0]);
            }
            if(stats.getExclusionType().equals(ExclusionType.MUTE)) {
                cs.sendMessage("§cGemutet: §f" + args[0]);
            }

            cs.sendMessage("§4Grund: " + "§f" + stats.getReasonType() + " (" + stats.getExtraReason() + " )");

            Time time = new Time(stats.getEnd());
            String end = time.toLocaleString();
            if(stats.getEnd() == -1) {
                end = "PERMANENT";
            }

            cs.sendMessage("§4EntbannungsDatum: §f" + end);





            return true;
        }











        cs.sendMessage(Main.prefix + "§cSyntax: §7/ban <Spieler> <Grund> (GrundDetails)");
        return false;
    }
}
