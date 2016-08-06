package me.anticheat.listener;

import com.mongodb.client.model.Filters;
import me.anticheat.Main;
import me.anticheat.ban.BanManager;
import me.anticheat.ban.BanStats;
import me.anticheat.ban.ExclusionType;
import me.anticheat.chat.Last3MSG;
import me.anticheat.chat.MuteManager;
import me.anticheat.chat.UpperCase;
import me.anticheat.log.Log;
import me.anticheat.log.LogType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.Time;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Jakob on 26.03.2016.
 */
public class Listener_AsyncPlayerChatEvent implements Listener{

    public static HashMap<UUID, Last3MSG> lastmsg = new HashMap();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();

        //MUTED CHECK
        if(MuteManager.isMuteList(id)) {
            e.setCancelled(MuteManager.onTalk(p));
            return;
        }

        //ERSTELLE LOG
        String msg = e.getMessage().toLowerCase();
        Time t = new Time(System.currentTimeMillis());
        new Log(t.getHours(), t.getMinutes(), p.getName(), LogType.CHAT, msg);


        //UPPERCASE CHECK
        if(UpperCase.isUpperCase(e.getMessage())) {
            p.sendMessage(Main.prefix + "§cDas sind zu viele Großbuchstaben!");
            e.setCancelled(true);
            return;
        }

        //WIEDERHOLUNG CHECK
        Last3MSG last3msg = lastmsg.get(id);
        if(last3msg == null) {
            lastmsg.put(id, new Last3MSG(null,null,null));
            last3msg = lastmsg.get(id);
        }
        last3msg.addMSG(msg);
        if(last3msg.isTwoSame()) {
            p.sendMessage(Main.prefix + "§cWiederhole dich nicht!");
            if(last3msg.isSame()) {
                p.sendMessage(Main.prefix + "§cDu wurdest gemutet!");
                BanStats stats = new BanStats(ExclusionType.MUTE, null, System.currentTimeMillis() + 1000*60*5, null, "WIEDERHOLUNG");
                BanManager.ban(id, stats);
                e.setCancelled(true);
                return;
            }
        }








    }



}
