package me.anticheat.commands;

import me.anticheat.Main;
import me.anticheat.api.UUIDConverter;
import me.anticheat.ban.BanManager;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * Created by Jakob on 03.04.2016.
 */
public class Command_Unban implements CommandExecutor{


    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {

        if(cs.hasPermission("vetoxchat.unban")) {

            if(args.length == 1) {

                UUID id = UUID.fromString(UUIDConverter.getValue(args[0]));

                Document document = BanManager.getBanFile(id);
                if(document == null) {
                    cs.sendMessage(Main.prefix + "§cDer Spieler ist nich gebannt!");
                    return true;
                }

                Document doc = BanManager.getBanFile(id);
                doc.put("currentban", null);
                Main.mongoClient.getDatabase("vetoxdb").getCollection("vetoxbans").replaceOne(document, doc);
                cs.sendMessage(Main.prefix + "§7Du hast den Spieler entbannt!");

            } else {
                cs.sendMessage(Main.prefix + "§7Syntax: §7/unban <Spieler>!");
            }



        }


        return false;
    }
}
