package me.anticheat;


import com.mongodb.MongoClient;
import me.anticheat.commands.Command_Ban;
import me.anticheat.commands.Command_ChatReport;
import me.anticheat.commands.Command_Mute;
import me.anticheat.commands.Command_Unban;
import me.anticheat.listener.Listener_AsyncPlayerChatEvent;
import me.anticheat.listener.Listener_PlayerLogginEvent;
import me.anticheat.log.LogManager;
import me.anticheat.mongodb.DataBaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

    public static final String prefix = "§4[§7VetoxCheat§4] ";
    public static MongoClient mongoClient = new MongoClient("localhost", 27017);
    public static Main main;


    @Override
    public void onEnable() {

        this.main = this;

        registerCommands();
        registerEvents();
        LogManager.createDirections();
        LogManager.saveLogs(main);

        DataBaseManager.createCollection();

        Bukkit.getConsoleSender().sendMessage("§cDas VetoxCheat plugin wurde aktiviert!");



    }

    @Override
    public void onDisable() {
        LogManager.saveLogsOnce(this);
    }

    private void registerCommands() {
        getCommand("ban").setExecutor(new Command_Ban());
        getCommand("chatreport").setExecutor(new Command_ChatReport());
        getCommand("mute").setExecutor(new Command_Mute());
        getCommand("unban").setExecutor(new Command_Unban());
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        //pm.registerEvents(new Listener_PlayerChatEvent(), this);
        pm.registerEvents(new Listener_PlayerLogginEvent(), this);
        pm.registerEvents(new Listener_AsyncPlayerChatEvent(), this);
    }
}
