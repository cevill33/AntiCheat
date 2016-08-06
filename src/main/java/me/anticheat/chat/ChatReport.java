package me.anticheat.chat;

import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.TextComponent;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Jakob on 26.03.2016.
 */
public class ChatReport {

    public static ArrayList<ChatReport> reports = new ArrayList<ChatReport>();

    private String reporter;
    private String victim;
    private UUID victimuuid;
    private String reason;
    private ArrayList<String> msg;
    private String id;

    public ChatReport(String reporter, String victim, UUID victimuuid, String reason, ArrayList<String> msg) {
        this.reporter = reporter;
        this.victim = victim;
        this.reason = reason;
        this.msg = msg;
        this.victimuuid = victimuuid;
        reports.add(this);
        this.id = "" + System.currentTimeMillis() + new Random().nextInt(40);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if(p.hasPermission("vetoxcheat.mute.getreports")) {

                p.sendMessage("§3Report: ");
                p.sendMessage(" §fVon: §7" + this.getReporter());
                p.sendMessage(" §fAn: §7" + this.getVictim());
                p.sendMessage(" §fGrund: §7" + this.getReason());
                net.md_5.bungee.api.chat.TextComponent text = new net.md_5.bungee.api.chat.TextComponent(" §fMuten: ");

                net.md_5.bungee.api.chat.TextComponent beleidigung = new net.md_5.bungee.api.chat.TextComponent(" §4§l[BELEIDIGUNG]");
                beleidigung.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute idbeleidigung " + this.getId()));

                net.md_5.bungee.api.chat.TextComponent provokation = new net.md_5.bungee.api.chat.TextComponent(" §c§l[PROVOKATION]");
                provokation.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute idprovokation " + this.getId()));

                net.md_5.bungee.api.chat.TextComponent werbung = new net.md_5.bungee.api.chat.TextComponent(" §6§l[WERBUNG]");
                werbung.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute idwerbung " + this.getId()));

                net.md_5.bungee.api.chat.TextComponent no = new net.md_5.bungee.api.chat.TextComponent(" §a§l[NEIN]");
                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute idno " + this.getId()));

                text.addExtra(beleidigung);
                text.addExtra(provokation);
                text.addExtra(werbung);
                text.addExtra(no);

                p.spigot().sendMessage(text);

                p.sendMessage(" §fChatverlauf: ");
                for (String m : this.getMsg()) {
                    p.sendMessage("   §e- §7" + m);
                }




            }
        }


    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getVictim() {
        return victim;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ArrayList<String> getMsg() {
        return msg;
    }

    public void setMsg(ArrayList<String> msg) {
        this.msg = msg;
    }

    public UUID getVictimuuid() {
        return victimuuid;
    }

    public void setVictimuuid(UUID victimuuid) {
        this.victimuuid = victimuuid;
    }

    public String getId() {
        return id;
    }
}
