package me.michaeldevc.experiencecore;


import me.michaeldevc.experiencecore.Modules.*;
import me.michaeldevc.experiencecore.Utils.PluginLogger;
import me.michaeldevc.experiencecore.Utils.TabCompleteAdmin;
import me.michaeldevc.experiencecore.Utils.TabCompleteCavaliere;
import org.bukkit.plugin.java.JavaPlugin;


public final class Cores extends JavaPlugin {

    @Override
    public void onEnable() {
        //Commands
        this.getCommand("chat").setExecutor(new Commands());
        this.getCommand("cestino").setExecutor(new Commands());
        this.getCommand("meteo").setExecutor(new Commands());
        this.getCommand("libroclaim").setExecutor(new Commands());
        this.getCommand("experiencecore").setExecutor(new Commands());
        this.getCommand("cavaliere").setExecutor(new Commands());

        this.getCommand("msg").setExecutor(new ChatUtils());
        this.getCommand("r").setExecutor(new ChatUtils());
        this.getCommand("tpa").setExecutor(new ChatUtils());
        this.getCommand("tpahere").setExecutor(new ChatUtils());


        //TabCompleter
        this.getCommand("experiencecore").setTabCompleter(new TabCompleteAdmin());
        this.getCommand("cavaliere").setTabCompleter(new TabCompleteCavaliere());


        //Modules
        this.getServer().getPluginManager().registerEvents(new Emojis(), this);
        this.getServer().getPluginManager().registerEvents(new SelfToggles(), this);
        this.getServer().getPluginManager().registerEvents(new SecureBlock(), this);
        this.getServer().getPluginManager().registerEvents(new ChatUtils(), this);
        this.getServer().getPluginManager().registerEvents(new ChatLogger(), this);


        //Config
        saveDefaultConfig();


        //Logging
        PluginLogger.log("DISABLED",
                "\n\n" +
                "  _____                      _                      ____               \n" +
                        " | ____|_  ___ __   ___ _ __(_) ___ _ __   ___ ___ / ___|___  _ __ ___ \n" +
                        " |  _| \\ \\/ / '_ \\ / _ \\ '__| |/ _ \\ '_ \\ / __/ _ \\ |   / _ \\| '__/ _ \\\n" +
                        " | |___ >  <| |_) |  __/ |  | |  __/ | | | (_|  __/ |__| (_) | | |  __/\n" +
                        " |_____/_/\\_\\ .__/ \\___|_|  |_|\\___|_| |_|\\___\\___|\\____\\___/|_|  \\___|\n" +
                        "            |_|                                                        " +
                        "\n\n"
                );

        PluginLogger.log("INFO", "Plugin version: v" + this.getDescription().getVersion());
        PluginLogger.log("INFO", "A multi-purpose Plugin to manage your Minecraft server!");
        PluginLogger.log("INFO", "Made by MichaelDevC_#0360");
        PluginLogger.log("INFO", "Plugin successfully loaded!");
    }
}
