package me.anticheat.ban;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import me.anticheat.Main;
import me.anticheat.chat.MuteManager;
import me.anticheat.log.Log;
import me.anticheat.log.LogType;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.sql.Time;
import java.util.UUID;

public class BanManager {


    public static boolean isBanned(UUID uuid) {

        MongoCollection mongoCollection = Main.mongoClient.getDatabase("vetoxdb").getCollection("vetoxbans");
        FindIterable<Document> find = mongoCollection.find(Filters.eq("uuid", uuid.toString()));

        Document document = find.first();
        if(document != null) {
            if(document.get("currentban") != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMuted(UUID uuid) {

        MongoCollection mongoCollection = Main.mongoClient.getDatabase("vetoxdb").getCollection("vetoxbans");
        FindIterable<Document> find = mongoCollection.find(Filters.eq("uuid", uuid.toString()));

        Document document = find.first();
        if(document != null) {
            if(document.get("currentmute") != null) {
                return true;
            }
        }
        return false;
    }

    public static Document getBanFile(UUID uuid) {

        MongoCollection mongoCollection = Main.mongoClient.getDatabase("vetoxdb").getCollection("vetoxbans");
        FindIterable<Document> find = mongoCollection.find(Filters.eq("uuid", uuid.toString()));

        Document document = find.first();
        return document;
    }

    public static void ban(UUID uuid, BanStats stats) {

        if(stats == null) {
            System.out.println("Stats ist gleich NULL!");
        }

        //Bei Bann:
        if(stats.getExclusionType().equals(ExclusionType.BAN)) {

            Time time = new Time(System.currentTimeMillis());

            String banner = "CONSOLE";
            if(stats.getBanner() != null) {
                banner = stats.getBanner().toString();
            }
            new Log(time.getHours(), time.getMinutes(), banner, LogType.BAN, uuid + " wurde gebannt wegen: "+stats.getReasonType().toString()+"(" + stats.getExtraReason() + ")");

            Player p = Bukkit.getPlayer(uuid);
            if(p != null) {

                String end = "1.1.2099";
                if (stats.getEnd() != -1) {
                    time = new Time(stats.getEnd());
                    end = time.toLocaleString();
                }

                p.kickPlayer("§3VetoxMC.DE \n §4Du wurdest von VetoxMC gebannt. \n \n§4Grund: §f" + stats.getReasonType().toString() + " (" + stats.getExtraReason() + " ) \n §4Entbannungsdatum:§f " + end + "\n \n §aDu kannst" +
                        " bei einem unfairen Ban in unseren TS kommen.");
                Bukkit.broadcastMessage(Main.prefix + "§4Der Spieler " + p.getName() + " wurde gebannt!");
            }

            //MongoDB Teil:
            Document document = getBanFile(uuid);
            MongoCollection collection = Main.mongoClient.getDatabase("vetoxdb").getCollection("vetoxbans");
            if(document == null) {
                System.out.println("Das Dokument war NULL.");
                document = new Document();
                document.put("uuid", uuid.toString());
                document.put("banpoints", 0);
                document.put("mutepoints", 0);
                System.out.println("Punkte wurden gesetzt.");
                collection.insertOne(document);
            }

            Document doc = getBanFile(uuid);
            if(doc == null) {
                System.out.println("Das zweite Document ist NUll.");
            }

            doc.put("banpoints", (Integer) doc.get("banpoints") + 1);
            Document docban = new Document();
            docban.put("end", stats.getEnd());
            String reason = "";
            if(stats.getReasonType() != null) {
                reason = stats.getReasonType().toString();
            }
            docban.put("reason", reason);
            docban.put("extrareason", stats.getExtraReason());
            banner = null;
            if(stats.getBanner() != null) {
                banner = stats.getBanner().toString();
            }
            docban.put("banner", banner);

            doc.put("currentban", docban);
            collection.replaceOne(document, doc);

            return;

        }


        //Bei Mute
        if(stats.getExclusionType().equals(ExclusionType.MUTE)) {
            Time time = new Time(System.currentTimeMillis());

            String banner = "CONSOLE";
            if(stats.getBanner() != null) {
                banner = stats.getBanner().toString();
            }

            String reason = "";
            if(stats.getReasonType() != null) {
                System.out.println("Der Reason ist nicht NULL!");
                reason = stats.getReasonType().toString();
            }

            new Log(time.getHours(), time.getMinutes(), banner, LogType.MUTE, uuid + " wurde gebannt wegen: " + reason +"(" + stats.getExtraReason() + ")");

            Document document = getBanFile(uuid);
            MongoCollection collection = Main.mongoClient.getDatabase("vetoxdb").getCollection("vetoxbans");
            if(document == null) {
                document = new Document();
                document.put("uuid", uuid.toString());
                document.put("banpoints", 0);
                document.put("mutepoints", 0);
                collection.insertOne(document);
            }

            Document doc = getBanFile(uuid);
            Document docmute = new Document();
            docmute.put("end", stats.getEnd());
            docmute.put("reason", reason);
            docmute.put("extrareason", stats.getExtraReason());
            banner = null;
            if(stats.getBanner() != null) {
                banner = stats.getBanner().toString();
            }

            docmute.put("banner", banner);

            if(stats.getReasonType() != null && stats.getReasonType() != Reason.ASK) {
                doc.put("mutepoints", (Integer) doc.get("mutepoints") + 1);
            }

            doc.put("currentmute", docmute);
            MuteManager.muted.add(uuid);

            collection.replaceOne(document, doc);


        }





    }








}
