package me.anticheat.chat;

import com.mongodb.client.MongoCollection;
import me.anticheat.Main;
import me.anticheat.ban.BanManager;
import org.bson.Document;
import org.bukkit.entity.Player;

import javax.print.Doc;
import java.sql.Time;
import java.util.ArrayList;
import java.util.UUID;

public class MuteManager {

    public static ArrayList<UUID> muted = new ArrayList<UUID>();

    public static boolean isMuteList(UUID uuid) {
        if(muted.contains(uuid)) return true;
        return false;
    }


    public static boolean onTalk(Player p) {
        long now = System.currentTimeMillis();
        UUID id = p.getUniqueId();
        Document doc = BanManager.getBanFile(id);
        Document currentmute = (Document) doc.get("currentmute");

        Object endobj = currentmute.get("end");
        if(endobj == null) return false;

        long end = (Long) endobj;

        //Wenn noch gemuted:
        if(end > now) {

            String mute = (String) currentmute.get("extrareason");
            if(mute.equalsIgnoreCase("ask")) {
                p.sendMessage("");
                p.sendMessage("  §cDu wurdest für 20min aus den Chat verbannt,");
                p.sendMessage("  §cda du eine frage nicht richtig gestellt hast!");
                p.sendMessage("  §aTipp: §fWenn du eine Frage hast gebe ? + Frage in den Chat ein");
                p.sendMessage("");
                return true;
            }
            p.sendMessage(Main.prefix + "§cDu bis gemutet!");
            p.sendMessage("§c  Grund: §f" + currentmute.get("reason") + " (" + currentmute.get("extrareason") + " )");
            p.sendMessage("§c  Gemutet bis: §f" + new Time(end).toLocaleString());
            return true;
        } else {
            //Wenn Zeit vorbei:
            if(isMuteList(id)) { muted.remove(id); }
            MongoCollection mongoCollection = Main.mongoClient.getDatabase("vetoxdb").getCollection("vetoxbans");
            Document document = BanManager.getBanFile(id);
            document.remove("currentmute");
            mongoCollection.replaceOne(doc, document);
        }
        return false;
    }




}
