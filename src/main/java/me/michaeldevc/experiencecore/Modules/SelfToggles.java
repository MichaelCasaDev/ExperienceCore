package me.michaeldevc.experiencecore.Modules;

import me.michaeldevc.experiencecore.Utils.PluginLogger;
import me.michaeldevc.experiencecore.Utils.Variables;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class SelfToggles implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Configuration config = Bukkit.getServer().getPluginManager().getPlugin("ExperienceCore").getConfig();

        applySelfToggles(config);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Configuration config = Bukkit.getServer().getPluginManager().getPlugin("ExperienceCore").getConfig();

        applySelfToggles(config);
    }

    private void applySelfToggles(Configuration config){
        if(Variables.selfToggles){
            int totalPlayer = Bukkit.getOnlinePlayers().size() -1;

            for(String levelx : config.getConfigurationSection("SelfTogglesValues").getKeys(false)){
                int viewDistanceTick = config.getInt("SelfTogglesValues." + levelx + ".Tick");
                int viewDistanceNoTick = config.getInt("SelfTogglesValues." + levelx + ".NoTick");

                int spawnMonsters = config.getInt("SelfTogglesValues." + levelx + ".Monsters");
                int spawnAnimals = config.getInt("SelfTogglesValues." + levelx + ".Animals");


                if(levelx.equalsIgnoreCase(String.valueOf(totalPlayer))){
                    for(World w : Bukkit.getWorlds()){
                        w.setNoTickViewDistance(viewDistanceNoTick);
                        w.setViewDistance(viewDistanceTick);
                    }

                    Bukkit.getWorlds().forEach(wrld -> {
                        wrld.setMonsterSpawnLimit(spawnMonsters);
                    });

                    Bukkit.getWorlds().forEach(wrld -> {
                        wrld.setAnimalSpawnLimit(spawnAnimals);
                    });


                    PluginLogger.log("WARNING", "SelfToggles applied! Level [" + levelx + "]");
                }
            }
        }
    }
}
