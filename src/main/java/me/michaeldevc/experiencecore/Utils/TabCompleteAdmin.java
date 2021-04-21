package me.michaeldevc.experiencecore.Utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabCompleteAdmin implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> commands = new ArrayList<>();
                commands.add("reload");
                commands.add("entityspawning");
                commands.add("entityinfo");
                commands.add("thanos");
                commands.add("viewdistance");
                commands.add("selftoggles");
                commands.add("emergency");

                return commands;
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("entityspawning")) {
                    List<String> argumentsMobAnimalsSpawning = new ArrayList<>();
                    argumentsMobAnimalsSpawning.add("mobs");
                    argumentsMobAnimalsSpawning.add("animals");
                    argumentsMobAnimalsSpawning.add("get");
                    return argumentsMobAnimalsSpawning;
                }
                if (args[0].equalsIgnoreCase("puff")) {
                    List<String> argumentsOnline = new ArrayList<>();
                    for (Player p : Bukkit.getOnlinePlayers())
                        argumentsOnline.add(p.getName());
                    return argumentsOnline;
                }
                if (args[0].equalsIgnoreCase("viewdistance")) {
                    List<String> argumentsInfo = new ArrayList<>();
                    argumentsInfo.add("notick");
                    argumentsInfo.add("tick");
                    argumentsInfo.add("get");
                    return argumentsInfo;
                }
                if (args[0].equalsIgnoreCase("selftoggles")) {
                    List<String> arguments = new ArrayList<>();
                    arguments.add("true");
                    arguments.add("false");
                    return arguments;
                }
                List<String> nulling = new ArrayList<>();
                nulling.add("");
                return nulling;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("entityspawning")) {
                    if (args[1].equalsIgnoreCase("mobs") || args[1].equalsIgnoreCase("animals")) {
                        List<String> mobSpawningSubSub = new ArrayList<>();
                        mobSpawningSubSub.add("set");
                        mobSpawningSubSub.add("disable");
                        return mobSpawningSubSub;
                    }
                } else {
                    if (args[0].equalsIgnoreCase("puff")) {
                        List<String> reason = new ArrayList<>();
                        reason.add("Ragione");
                        return reason;
                    }
                    if (args[0].equalsIgnoreCase("viewdistance")) {
                        List<String> values = new ArrayList<>();
                        values.add("2");
                        values.add("32");
                        return values;
                    }
                }
            } else if (args.length == 4) {
                List<String> nulling = new ArrayList<>();
                nulling.add("");
                return nulling;
            }
        } else if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            if (args.length == 1) {
                List<String> commands = new ArrayList<>();
                commands.add("reload");
                commands.add("entityspawning");
                commands.add("entityinfo");
                return commands;
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("entityspawning")) {
                    List<String> argumentsMobAnimalsSpawning = new ArrayList<>();
                    argumentsMobAnimalsSpawning.add("mobs");
                    argumentsMobAnimalsSpawning.add("animals");
                    return argumentsMobAnimalsSpawning;
                }
                if (args[0].equalsIgnoreCase("puff")) {
                    List<String> argumentsOnline = new ArrayList<>();
                    for (Player p : Bukkit.getOnlinePlayers())
                        argumentsOnline.add(p.getName());
                    return argumentsOnline;
                }
                List<String> nulling = new ArrayList<>();
                nulling.add("");
                return nulling;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("entityspawning") && (
                        args[1].equalsIgnoreCase("mobs") || args[1].equalsIgnoreCase("animals"))) {
                    List<String> mobSpawningSubSub = new ArrayList<>();
                    mobSpawningSubSub.add("set");
                    mobSpawningSubSub.add("disable");
                    return mobSpawningSubSub;
                }
            } else if (args.length == 4) {
                List<String> nulling = new ArrayList<>();
                nulling.add("");
                return nulling;
            }
        }
        return null;
    }
}
