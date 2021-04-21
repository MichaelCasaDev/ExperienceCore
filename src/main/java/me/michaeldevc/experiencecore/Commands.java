package me.michaeldevc.experiencecore;

import me.michaeldevc.experiencecore.Utils.PluginLogger;
import me.michaeldevc.experiencecore.Utils.Variables;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.Console;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

public class Commands implements CommandExecutor {
    Configuration config = Bukkit.getServer().getPluginManager().getPlugin("ExperienceCore").getConfig();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;


            if(command.getName().equalsIgnoreCase("chat")){ // /chat
                player.performCommand("colors");
            } else if(command.getName().equalsIgnoreCase("cestino")){ // /cestino
                player.performCommand("disposal");
            } else if(command.getName().equalsIgnoreCase("meteo")){ // /meteo
                player.performCommand("voting");
            } else if(command.getName().equalsIgnoreCase("libroclaim")) { // /libroclaim
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String consoleCommand = "claimbook " + player.getName();
                Bukkit.dispatchCommand(console, consoleCommand);
            } else if(command.getName().equalsIgnoreCase("cavaliere")){ // /cavaliere effetto <type> <value>
                cavaliereCommand(player, config, args);
            } else if(command.getName().equalsIgnoreCase("experiencecore")){ // /experiencecore
                if(args.length == 0){
                    if(player.hasPermission("experiencecore.help")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("DefaultMessage")));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermissions")));
                    }

                    return true;
                }

                if(args.length >= 1){
                    if(args[0].equalsIgnoreCase("reload")){ // /experiencecore reload
                        reloadComand(player, config);
                    } else if (args[0].equalsIgnoreCase("entityspawning")) { // /experiencecore entityspawning <mobs | animals> <set | disabled>
                        entitySpawningComand(player, config, args);
                    } else if (args[0].equalsIgnoreCase("entityinfo")) { // /experiencecore entityinfo
                        entityInfoComand(player, config);
                    } else if (args[0].equalsIgnoreCase("thanos")) { // /experiencecore thanos
                        thanosComand(player, config);
                    } else if (args[0].equalsIgnoreCase("xraytp")) { // /experiencecore xraytp <player> [Inside command / Not show on TabComplete]
                        xrayTPCommand(player, config, args);
                    } else if (args[0].equalsIgnoreCase("viewdistance")) { // /experiencecore viewdistance <notick | tick> <value>
                        viewDistanceCommand(player, config, args);
                    } else if (args[0].equalsIgnoreCase("selftoggles")) { // /experiencecore selftoggles <true | false>
                        selfTogglesCommand(player, args);
                    } else if(args[0].equalsIgnoreCase("emergency")) { // /experiencecore emergency
                        if(player.hasPermission("experiencecore.admin")) {
                            int actualViewDistanceTick = 0;
                            int actualViewDistanceNoTick = 0;
                            int actualSpawnMonster = 0;
                            int actualSpawnAnimals = 0;

                            int delayConfig = config.getInt("Emergency.Duration");

                            int viewDistanceTick = config.getInt("Emergency.Values.ViewDistance.Tick");
                            int viewDistanceNoTick = config.getInt("Emergency.Values.ViewDistance.NoTick");
                            int spawnMonster = config.getInt("Emergency.Values.Spawning.Mobs");
                            int spawnAnimals = config.getInt("Emergency.Values.Spawning.Animals");

                            String titleEnable = config.getString("Emergency.BroadcastMessage.Enabling.MainTitle");
                            String subTitleEnable = config.getString("Emergency.BroadcastMessage.Enabling.SubTitle");

                            boolean enabledAudioEnable = config.getBoolean("Emergency.BroadcastMessage.Enabling.Audio.enabled");
                            String soundAudioEnable = config.getString("Emergency.BroadcastMessage.Enabling.Audio.sound");
                            int volumeAudioEnable = config.getInt("Emergency.BroadcastMessage.Enabling.Audio.volume");
                            int pitchAudioEnable = config.getInt("Emergency.BroadcastMessage.Enabling.Audio.pitch");

                            String titleDisabled = config.getString("Emergency.BroadcastMessage.Disabling.MainTitle");
                            String subTitleDisabled = config.getString("Emergency.BroadcastMessage.Disabling.SubTitle");

                            boolean enabledAudioDisabled = config.getBoolean("Emergency.BroadcastMessage.Disabling.Audio.enabled");
                            String soundAudioDisabled = config.getString("Emergency.BroadcastMessage.Disabling.Audio.sound");
                            int volumeAudioDisabled = config.getInt("Emergency.BroadcastMessage.Disabling.Audio.volume");
                            int pitchAudioDisabled = config.getInt("Emergency.BroadcastMessage.Disabling.Audio.pitch");

                            BossBar bossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', config.getString("Emergency.BossBar")), BarColor.RED, BarStyle.SOLID);


                            PluginLogger.log("WARNING", "E' stato attivato il sistema di emergenza!! Disattivazione in " + delayConfig + " secondi!");

                            // Implements values to all worlds
                            for(World w : Bukkit.getWorlds()){
                                actualViewDistanceTick = w.getViewDistance();
                                actualViewDistanceNoTick = w.getNoTickViewDistance();
                                actualSpawnMonster = w.getMonsterSpawnLimit();
                                actualSpawnAnimals = w.getAnimalSpawnLimit();

                                w.setViewDistance(viewDistanceTick);
                                w.setNoTickViewDistance(viewDistanceNoTick);

                                w.setMonsterSpawnLimit(spawnMonster);
                                w.setAnimalSpawnLimit(spawnAnimals);
                            }


                            // Send message to all online players when enabled
                            for(Player p : Bukkit.getOnlinePlayers()){
                                p.sendTitle(ChatColor.translateAlternateColorCodes('&', titleEnable.replace("%seconds%", String.valueOf(delayConfig))), ChatColor.translateAlternateColorCodes('&', subTitleEnable.replace("%seconds%", String.valueOf(delayConfig))), 10, config.getInt("Emergency.TitleStay"), 20);

                                bossBar.addPlayer(p);

                                if(enabledAudioEnable){
                                    p.playSound(p.getLocation(), Sound.valueOf(soundAudioEnable), volumeAudioEnable, pitchAudioEnable);
                                }
                            }


                            // Re-enabled all after specified time
                            int finalActualViewDistanceTick = actualViewDistanceTick;
                            int finalActualViewDistanceNoTick = actualViewDistanceNoTick;
                            int finalActualSpawnMonster = actualSpawnMonster;
                            int finalActualSpawnAnimals = actualSpawnAnimals;


                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    // Re-enable all old values
                                    for(World w : Bukkit.getWorlds()){
                                        w.setViewDistance(finalActualViewDistanceTick);
                                        w.setNoTickViewDistance(finalActualViewDistanceNoTick);

                                        w.setMonsterSpawnLimit(finalActualSpawnMonster);
                                        w.setAnimalSpawnLimit(finalActualSpawnAnimals);
                                    }
                                    // Send message to all online players when disabled automaticaly
                                    for(Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', titleDisabled), ChatColor.translateAlternateColorCodes('&', subTitleDisabled), 10, config.getInt("Emergency.TitleStay"), 20);

                                        bossBar.removeAll();

                                        if (enabledAudioDisabled) {
                                            p.playSound(p.getLocation(), Sound.valueOf(soundAudioDisabled), volumeAudioDisabled, pitchAudioDisabled);
                                        }
                                    }


                                    PluginLogger.log("WARNING", "Disattivazione automatica del sistema d'emergenza!");
                                }
                            }.runTaskLater(Bukkit.getPluginManager().getPlugin("ExperienceCore"), 20L * delayConfig);


                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermissions")));
                        }
                    }
                }
            }
        } else if(sender instanceof ConsoleCommandSender){
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            if(command.getName().equalsIgnoreCase("experiencecore")){
                if(args.length == 0){
                    console.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("DefaultMessage")));

                    return true;
                }

                if(args.length >= 1){
                    if(args[0].equalsIgnoreCase("reload")){ // /experiencecore reload
                        reloadCommandConsole(console, config);
                    } else if (args[0].equalsIgnoreCase("entityspawning")) { // /experiencecore entityspawning <mobs | animals> <set | disabled>
                        entitySpawningCommandConsole(console, config, args);
                    } else if (args[0].equalsIgnoreCase("entityinfo")) { // /experiencecore entityinfo
                        entityInfoCommandConsole(console, config);
                    } else if (args[0].equalsIgnoreCase("viewdistance")) { // /experiencecore viewdistance <notick | tick> <value>
                        viewDistanceCommandConsole(console, config, args);
                    } else if(args[0].equalsIgnoreCase("selftoggles")) { // /experiencecore selftoggles <true | false>
                        selfTogglesCommandConsole(console, args);
                    } else if(args[0].equalsIgnoreCase("thanos")) { // /experiencecore thanos
                        thanosComandConsole(console, config);
                    }
                }
            }
        }
        return true;
    }


    private void cavaliereCommand(Player player, Configuration config, String[] args){
        if(player.hasPermission("experienceVip.Cavaliere")){
            if(args.length >= 3){
                if(args[0].equalsIgnoreCase("effetto")){
                    if(args[1].equalsIgnoreCase("speed")){
                        if(args[2].equalsIgnoreCase("fly")){
                            if(args[3].equalsIgnoreCase("1")){
                                player.setFlySpeed((float) 0.1);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Fly.Set")
                                        .replace("%value%", "1")));
                            } else if(args[3].equalsIgnoreCase("2")){
                                player.setFlySpeed((float) 0.2);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Fly.Set")
                                        .replace("%value%", "2")));
                            } else if(args[3].equalsIgnoreCase("3")){
                                player.setFlySpeed((float) 0.3);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Fly.Set")
                                        .replace("%value%", "3")));
                            } else if(args[3].equalsIgnoreCase("rimuovi")){
                                player.setFlySpeed((float) 0.1);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Fly.Clear")));
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Info")));
                            }

                        } else if(args[2].equalsIgnoreCase("walk")) {
                            if (args[3].equalsIgnoreCase("1")) {
                                player.setWalkSpeed((float) 0.2);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Walk.Set")
                                        .replace("%value%", "1")));
                            } else if (args[3].equalsIgnoreCase("2")) {
                                player.setWalkSpeed((float) 0.3);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Walk.Set")
                                        .replace("%value%", "2")));
                            } else if (args[3].equalsIgnoreCase("3")) {
                                player.setWalkSpeed((float) 0.4);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Walk.Set")
                                        .replace("%value%", "3")));
                            } else if (args[3].equalsIgnoreCase("rimuovi")) {
                                player.setWalkSpeed((float) 0.2);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Walk.Clear")));
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Info")));
                            }
                        }
                    } else if(args[1].equalsIgnoreCase("haste")) {
                        if(args.length >= 1){
                            if (args[2].equals("1")) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2147483647, 0));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Haste.Activate").replace("%value%", "1")));
                            } else if (args[2].equals("2")) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2147483647, 1));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Haste.Activate").replace("%value%", "2")));
                            } else if (args[2].equals("3")) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2147483647, 2));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Haste.Activate").replace("%value%", "3")));
                            } else if (args[2].equals("rimuovi")) {
                                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Haste.Deactivate")));
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Haste.Info")));
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Haste.Info")));
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Cavaliere.Info")));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermissions")));
        }
    }


    //
    // Player
    //

    private void viewDistanceCommand(Player player, Configuration config, String[] args){
        if(player.hasPermission("experiencecore.admin")){
            if(args.length > 1
                    && args[1].equalsIgnoreCase("notick")
                    || args[1].equalsIgnoreCase("tick")
                    || args[1].equalsIgnoreCase("get")){
                if(args[1].equalsIgnoreCase("tick") && Integer.parseInt(args[2]) >= 2 && Integer.parseInt(args[2]) <= 32){
                    for(World w : Bukkit.getWorlds()){
                        w.setViewDistance(Integer.parseInt(args[2]));
                    }

                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    config.getString("ViewDistance.Message")
                                            .replace("%type%", "Tick")
                                            .replace("%value%", args[2])
                            )
                    );
                } else if(args[1].equalsIgnoreCase("notick") && Integer.parseInt(args[2]) >= 2 && Integer.parseInt(args[2]) <= 32){
                    for(World w : Bukkit.getWorlds()){
                        w.setNoTickViewDistance(Integer.parseInt(args[2]));
                    }

                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                config.getString("ViewDistance.Message")
                                    .replace("%type%", "No-Tick")
                                    .replace("%value%", args[2])
                            )
                    );
                } else if(args[1].equalsIgnoreCase("get")){
                    player.sendMessage("\n\nTick ViewDistance");
                    for(World w : Bukkit.getWorlds()){
                        player.sendMessage("World: " + w.getName() + " | " + w.getViewDistance());
                    }

                    player.sendMessage("\n\nNoTick ViewDistance");
                    for(World w : Bukkit.getWorlds()){
                        player.sendMessage("World: " + w.getName() + " | " + w.getNoTickViewDistance());
                    }
                } else {
                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    config.getString("ViewDistance.NoArgs")
                            )
                    );
                }
            } else {
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                config.getString("ViewDistance.NoArgs")
                        )
                );
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("NoPermissions")
            ));
        }
    }

    private void xrayTPCommand(Player player, Configuration config, String[] args){
        if(player.hasPermission("experiencecore.xraytp")){
            if(args[1] != null){
                Player playerIncriminated = Bukkit.getPlayer(args[1]);

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', config.getString("StaffMessages.Teleport"))
                );

                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(playerIncriminated.getLocation());
            } else {
                player.sendMessage("Error command!");
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("NoPermissions")
            ));
        }
    }

    private void reloadComand(Player player, Configuration config){
        if(player.hasPermission("experiencecore.admin")){
            Bukkit.getPluginManager().getPlugin("ExperienceCore").reloadConfig();

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("Reload")
            ));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("NoPermissions")
            ));
        }
    }

    private void thanosComand(Player player, Configuration config){
        if(player.hasPermission("experiencecore.admin")) {

            Random r = new Random();
            for (World w : Bukkit.getWorlds()) {
                if (w.getName().contains("world")) {
                    for (Entity e : w.getEntities()) {
                        if (e instanceof LivingEntity &&
                                !(e instanceof Player) &&
                                !(e instanceof ArmorStand) &&
                                !(e instanceof Villager) &&
                                !(e instanceof IronGolem) &&
                                !(e instanceof Cat) &&
                                !(e instanceof Wolf) &&
                                !(e instanceof Ocelot) &&
                                !(e instanceof ItemFrame) &&
                                !(e instanceof Horse) &&
                                e.getCustomName() == null) {
                            if(!((Tameable) e ).isTamed()){
                                if(e.getCustomName() == null){
                                    if (r.nextBoolean()) {
                                        e.remove();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                if(config.getBoolean("Thanos.Audio.enable")){
                    p.playSound(p.getLocation(),
                            Sound.valueOf(config.getString("Thanos.Audio.sound")),
                            config.getInt("Thanos.Audio.volume"),
                            config.getInt("Thanos.Audio.pitch")
                    );
                }

                p.sendTitle(ChatColor.translateAlternateColorCodes('&', config.getString("Thanos.MainTitle")),
                        config.getString("Thanos.SubTitle"), 40, 50, 20);
            }

            PluginLogger.log("WARNING", player.getName() + " used Thanos!");
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("NoPermissions")
            ));
        }
    }

    private void entityInfoComand(Player player, Configuration config){
        if(player.hasPermission("experiencecore.admin")) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            HashMap<String, Integer> entityseparate = new HashMap<String, Integer>();
            double total = 0;
            for (World w : Bukkit.getWorlds())
                for (Entity ent : w.getEntities()) {
                    if (entityseparate.containsKey(ent.getType().getKey().getKey())) {
                        entityseparate.put(ent.getType().getKey().getKey(),
                                entityseparate.get(ent.getType().getKey().getKey()) + 1);
                    } else
                        entityseparate.put(ent.getType().getKey().getKey(), 1);
                    total++;
                }

            player.sendMessage(config.getString("EntityInfoMessages.Header"));
            for (Map.Entry<String, Integer> entry : entityseparate.entrySet()) {
                player.sendMessage(
                        df.format((float) ((float) entry.getValue() / total) * 100) + "% di " + entry.getKey());
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("NoPermissions")
            ));
        }
    }

    private void entitySpawningComand(Player player, Configuration config, String[] args){
        if(player.hasPermission("experiencecore.admin")){
            if(args[1].equalsIgnoreCase("mobs")){
                if (args[2].equalsIgnoreCase("set")) {
                    if (args.length == 4) {
                        int mobSpawn = Integer.parseInt(args[3]);
                        Bukkit.getWorlds().forEach(wrld -> {
                            wrld.setMonsterSpawnLimit(mobSpawn);
                        });

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                config.getString("EntitySpawningMessages.Mobs").replace("%amount%", String.valueOf(mobSpawn))));
                    }
                } else if (args[2].equalsIgnoreCase("disable")) {
                    Bukkit.getWorlds().forEach(wrld -> {
                        wrld.setMonsterSpawnLimit(0);
                    });
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("EntitySpawningMessages.Disabled_Mobs")));
                }
            } else if (args[1].equalsIgnoreCase("animals")) {
                if (args[2].equalsIgnoreCase("set")) {
                    if (args.length == 4) {
                        int animalSpawn = Integer.parseInt(args[3]);
                        Bukkit.getWorlds().forEach(wrld -> {
                            wrld.setAnimalSpawnLimit(animalSpawn);
                        });

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                config.getString("EntitySpawningMessages.Animals").replace("%amount%", String.valueOf(animalSpawn))));
                    }
                } else if (args[2].equalsIgnoreCase("disable")) {
                    Bukkit.getWorlds().forEach(wrld -> {
                        wrld.setAnimalSpawnLimit(0);
                    });
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("EntitySpawningMessages.Disabled_Animals")));
                }
            } else if(args[1].equalsIgnoreCase("get")){
                player.sendMessage("\n\nMob");

                Bukkit.getWorlds().forEach(w -> {
                    player.sendMessage("World: " + w.getName() + " | " + w.getMonsterSpawnLimit());
                });

                player.sendMessage("\n\nAnimals");
                Bukkit.getWorlds().forEach(w -> {
                    player.sendMessage("World: " + w.getName() + " | " + w.getAnimalSpawnLimit());
                });
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("NoPermissions")
            ));
        }
    }

    private void selfTogglesCommand(Player player, String[] args){
        if(player.hasPermission("experiencecore.admin")){
            if(args[1].equalsIgnoreCase("true")){
                Variables.selfToggles = true;

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', config.getString("SelfTogglesMessages.Activated")
                ));
            } else if(args[1].equalsIgnoreCase("false")){
                Variables.selfToggles = false;

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', config.getString("SelfTogglesMessages.Deactivated")
                ));
            } else {
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', config.getString("ErrorMessage")
                ));
            }
        } else {
            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', config.getString("NoPermissions")
            ));
        }
    }

    //
    // Console
    //

    private void reloadCommandConsole(ConsoleCommandSender console, Configuration config){
        Bukkit.getPluginManager().getPlugin("ExperienceCore").reloadConfig();

        console.sendMessage(ChatColor.translateAlternateColorCodes('&',
                config.getString("Reload")
        ));
    }

    private void entitySpawningCommandConsole(ConsoleCommandSender console, Configuration config, String[] args){
        if(args[1].equalsIgnoreCase("mobs")){
            if (args[2].equalsIgnoreCase("set")) {
                if (args.length == 4) {
                    int mobSpawn = Integer.parseInt(args[3]);
                    Bukkit.getWorlds().forEach(wrld -> {
                        wrld.setMonsterSpawnLimit(mobSpawn);
                    });

                    console.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            config.getString("EntitySpawningMessages.Mobs").replace("%amount%", String.valueOf(mobSpawn))));
                }
            } else if (args[2].equalsIgnoreCase("disable")) {
                Bukkit.getWorlds().forEach(wrld -> {
                    wrld.setMonsterSpawnLimit(0);
                });
                console.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("EntitySpawningMessages.Disabled_Mobs")));
            }
        } else if (args[1].equalsIgnoreCase("animals")) {
            if (args[2].equalsIgnoreCase("set")) {
                if (args.length == 4) {
                    int animalSpawn = Integer.parseInt(args[3]);
                    Bukkit.getWorlds().forEach(wrld -> {
                        wrld.setAnimalSpawnLimit(animalSpawn);
                    });

                    console.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            config.getString("EntitySpawningMessages.Animals").replace("%amount%", String.valueOf(animalSpawn))));
                }
            } else if (args[2].equalsIgnoreCase("disable")) {
                Bukkit.getWorlds().forEach(wrld -> {
                    wrld.setAnimalSpawnLimit(0);
                });
                console.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("EntitySpawningMessages.Disabled_Animals")));
            }
        }
    }

    private void entityInfoCommandConsole(ConsoleCommandSender console, Configuration config){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        HashMap<String, Integer> entityseparate = new HashMap<String, Integer>();
        double total = 0;
        for (World w : Bukkit.getWorlds())
            for (Entity ent : w.getEntities()) {
                if (entityseparate.containsKey(ent.getType().getKey().getKey())) {
                    entityseparate.put(ent.getType().getKey().getKey(),
                            entityseparate.get(ent.getType().getKey().getKey()) + 1);
                } else
                    entityseparate.put(ent.getType().getKey().getKey(), 1);
                total++;
            }

        console.sendMessage(config.getString("EntityInfoMessages.Header"));
        for (Map.Entry<String, Integer> entry : entityseparate.entrySet()) {
            console.sendMessage(
                    df.format((float) ((float) entry.getValue() / total) * 100) + "% di " + entry.getKey());
        }
    }

    private void viewDistanceCommandConsole(ConsoleCommandSender console, Configuration config, String[] args){
        if(args.length == 3 && args[1].equalsIgnoreCase("notick") || args[1].equalsIgnoreCase("tick")){
            if(args[1].equalsIgnoreCase("tick") && Integer.parseInt(args[2]) >= 2 && Integer.parseInt(args[2]) <= 32){
                for(World w : Bukkit.getWorlds()){
                    w.setViewDistance(Integer.parseInt(args[2]));
                }

                console.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                config.getString("ViewDistance.Message")
                                        .replace("%type%", "Tick")
                                        .replace("%value%", args[2])
                        )
                );
            } else if(args[1].equalsIgnoreCase("notick") && Integer.parseInt(args[2]) >= 2 && Integer.parseInt(args[2]) <= 32){
                for(World w : Bukkit.getWorlds()){
                    w.setNoTickViewDistance(Integer.parseInt(args[2]));
                }

                console.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                config.getString("ViewDistance.Message")
                                        .replace("%type%", "No-Tick")
                                        .replace("%value%", args[2])
                        )
                );
            } else {
                console.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                config.getString("ViewDistance.NoArgs")
                        )
                );
            }
        } else {
            console.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            config.getString("ViewDistance.NoArgs")
                    )
            );
        }
    }

    private void selfTogglesCommandConsole(ConsoleCommandSender player, String[] args){
        if(args[1].equalsIgnoreCase("true")){
            Variables.selfToggles = true;

            player.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', config.getString("SelfTogglesMessages.Activated")
            ));
        } else if(args[1].equalsIgnoreCase("false")){
            Variables.selfToggles = false;

            player.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', config.getString("SelfTogglesMessages.Deactivated")
            ));
        }
    }

    private void thanosComandConsole(ConsoleCommandSender player, Configuration config){
        if(player.hasPermission("experiencecore.admin")) {

            Random r = new Random();
            for (World w : Bukkit.getWorlds()) {
                if (w.getName().contains("world")) {
                    for (Entity e : w.getEntities()) {
                        if (e instanceof LivingEntity &&
                                !(e instanceof Player) &&
                                !(e instanceof ArmorStand) &&
                                !(e instanceof Villager) &&
                                !(e instanceof IronGolem) &&
                                !(e instanceof Cat) &&
                                !(e instanceof Wolf) &&
                                !(e instanceof Ocelot) &&
                                !(e instanceof ItemFrame) &&
                                !(e instanceof Horse) &&
                                e.getCustomName() == null) {
                            if(!((Tameable) e ).isTamed()){
                                if(e.getCustomName() == null){
                                    if (r.nextBoolean()) {
                                        e.remove();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                if(config.getBoolean("Thanos.Audio.enable")){
                    p.playSound(p.getLocation(),
                            Sound.valueOf(config.getString("Thanos.Audio.sound")),
                            config.getInt("Thanos.Audio.volume"),
                            config.getInt("Thanos.Audio.pitch")
                    );
                }

                p.sendTitle(ChatColor.translateAlternateColorCodes('&', config.getString("Thanos.MainTitle")),
                        config.getString("Thanos.SubTitle"), 40, 50, 20);
            }

            PluginLogger.log("WARNING", player.getName() + " used Thanos!");
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("NoPermissions")
            ));
        }
    }
}