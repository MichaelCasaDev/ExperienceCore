package me.michaeldevc.experiencecore.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteCavaliere implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> arguments = new ArrayList<>();
                arguments.add("effetto");

                return arguments;
            } else if(args.length == 2){
                List<String> arguments = new ArrayList<>();
                arguments.add("speed");
                arguments.add("haste");

                return arguments;
            } else if(args.length == 3){
                if(args[1].equalsIgnoreCase("speed")){
                    List<String> arguments = new ArrayList<>();
                    arguments.add("fly");
                    arguments.add("walk");

                    return arguments;
                } else if(args[1].equalsIgnoreCase("haste")){
                    List<String> arguments = new ArrayList<>();
                    arguments.add("1");
                    arguments.add("2");
                    arguments.add("3");
                    arguments.add("rimuovi");

                    return arguments;
                }
            } else if(args.length == 4){
                if(args[1].equalsIgnoreCase("speed")){
                    List<String> arguments = new ArrayList<>();
                    arguments.add("1");
                    arguments.add("2");
                    arguments.add("3");
                    arguments.add("rimuovi");

                    return arguments;
                }
            } else if(args.length > 4){
                List<String> arguments = new ArrayList<>();
                arguments.add("");

                return arguments;
            }
        }


        return null;
    }
}

