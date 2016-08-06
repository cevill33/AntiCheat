package me.anticheat.listener;


import java.sql.Time;
import java.util.UUID;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import me.anticheat.Main;
import me.anticheat.ban.BanManager;
import me.anticheat.chat.Last3MSG;
import me.anticheat.chat.MuteManager;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class Listener_PlayerLogginEvent implements Listener {



    @SuppressWarnings("deprecation")
    @EventHandler
    public void onLoggin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();


        if(BanManager.isMuted(id)) {
            MuteManager.muted.add(id);
        }

        if(BanManager.isBanned(id)) {
            Document current = (Document) BanManager.getBanFile(id).get("currentban");
            long end = (Long) current.get("end");
            long now = System.currentTimeMillis();
            String reason = (String) current.get("reason");
            String extrareason = (String) current.get("extrareason");
            System.out.println("This is his End: " + end);


            //Wenn PERMANENT gebannt:
            if(end == -1) {
                e.disallow(Result.KICK_BANNED, "§3VetoxMC.DE \n §4Du wurdest von VetoxMC gebannt. \n \n§4Grund: §f" + reason + " (" + extrareason + " ) \n §4Dauer:§f PERMANENT  \n \n §aDu kannst" +
                        " bei einem unfairen Ban in unseren TS kommen.");
                return;
            }


            if(now < end) {
                e.disallow(Result.KICK_BANNED, "§3VetoxMC.DE \n §4Du wurdest von VetoxMC gebannt. \n \n§4Grund: §f" + reason + " (" + extrareason + " ) \n §4Entbannungsdatum:§f " + new Time(end).toLocaleString() + "\n \n §aDu kannst" +
                        " bei einem unfairen Ban in unseren TS kommen.");
                return;
            }

            //Wenn Zeit vorbei:
            if(now >= end) {
                Document document = BanManager.getBanFile(id);
                Document doc = BanManager.getBanFile(id);
                doc.put("currentban", null);
                Main.mongoClient.getDatabase("vetoxdb").getCollection("vetoxbans").replaceOne(document, doc);
                return;
            }



        }


    }
}



