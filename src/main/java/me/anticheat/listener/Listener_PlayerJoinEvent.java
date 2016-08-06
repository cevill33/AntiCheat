package me.anticheat.listener;

import me.anticheat.Main;
import me.anticheat.chat.ChatReport;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Jakob on 26.03.2016.
 */
public class Listener_PlayerJoinEvent implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission("vetoxcheat.mute.getreports")) {

            if(!ChatReport.reports.isEmpty()) p.sendMessage(Main.prefix + "§3Chatreports: ");

            for(ChatReport report : ChatReport.reports) {

                p.sendMessage("§3Report: ");
                p.sendMessage(" §fVon: §7" + report.getReporter());
                p.sendMessage(" §fAn: §7" + report.getVictim());
                p.sendMessage(" §fGrund: §7" + report.getReason());
                TextComponent text = new TextComponent(" §fMuten: ");

                TextComponent beleidigung = new TextComponent(" §4§l[BELEIDIGUNG]");
                beleidigung.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute idbeleidigung " + report.getId()));

                TextComponent provokation = new TextComponent(" §c§l[PROVOKATION]");
                provokation.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute idprovokation " + report.getId()));

                TextComponent werbung = new TextComponent(" §6§l[WERBUNG]");
                werbung.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute idwerbung " + report.getId()));

                TextComponent no = new TextComponent(" §a§l[NEIN]");
                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute idno " + report.getId()));

                text.addExtra(beleidigung);
                text.addExtra(provokation);
                text.addExtra(werbung);
                text.addExtra(no);

                p.spigot().sendMessage(text);

                p.sendMessage(" §fChatverlauf: ");
                for (String m : report.getMsg()) {
                    p.sendMessage("   §e- §7" + m);
                }

            }
        }






    }

}
