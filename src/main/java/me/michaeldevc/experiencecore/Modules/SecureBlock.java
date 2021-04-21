package me.michaeldevc.experiencecore.Modules;


import me.michaeldevc.experiencecore.Utils.TextBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class SecureBlock implements Listener {

    //Some blocks breaks!
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer(); // Player who broke the block
        Block blockBroke = event.getBlock(); // The block broke

        Configuration config = Bukkit.getServer().getPluginManager().getPlugin("ExperienceCore").getConfig();
        List staff = config.getStringList("Staff");
        List blocks = config.getStringList("Blocks");
        String breakMessage = config.getString("StaffMessages.Break");

        boolean soundEnable = config.getBoolean("StaffMessages.Audio.enable");
        String soundBreak = config.getString("StaffMessages.Audio.sound");
        int soundVolume = config.getInt("StaffMessages.Audio.volume");
        int soundPitch = config.getInt("StaffMessages.Audio.pitch");

        if(!player.hasPermission("experiencecore.secureblockbypass")) {
            if(blocks.contains(blockBroke.getType().name())){
                for(Object staffName : staff){ // Send to all staff
                    if(Bukkit.getServer().getPlayer(staffName.toString()) != null){ // Staff need to be online
                        Player staffer = Bukkit.getServer().getPlayer(staffName.toString());
                        Location locationStaffer = staffer.getLocation();

                        if(soundEnable){
                            staffer.playSound(locationStaffer, Sound.valueOf(soundBreak), soundVolume, soundPitch);
                        }


                        /*

                        TextBuilder builder = new TextBuilder( // Custom Text Builder to introduce things
                                ChatColor.translateAlternateColorCodes('&', breakMessage
                                        .replace("%player%", player.getName())
                                    .replace("%x%", "" + blockBroke.getLocation().getBlockX())
                                    .replace("%y%", "" + blockBroke.getLocation().getBlockY())
                                    .replace("%z%", "" + blockBroke.getLocation().getBlockZ())
                                    .replace("%block%", blockBroke.getType().name())
                                )
                        );


                        builder.setClickEvent( // Execute command on click
                                TextBuilder.ClickEventType.RUN_COMMAND, "/experiencecore xraytp " + player.getName()
                        );
                        builder.setHoverEvent( // Set a fancy hover text
                                TextBuilder.HoverEventType.SHOW_TEXT, ChatColor.translateAlternateColorCodes('&', config.getString("StaffMessages.Hover"))
                        );

                        builder.sendMessage(staffer); // Send message to the staffer

                        */

                        staffer.sendMessage(
                                ChatColor.translateAlternateColorCodes('&', breakMessage
                                        .replace("%player%", player.getName())
                                        .replace("%x%", "" + blockBroke.getLocation().getBlockX())
                                        .replace("%y%", "" + blockBroke.getLocation().getBlockY())
                                        .replace("%z%", "" + blockBroke.getLocation().getBlockZ())
                                        .replace("%block%", blockBroke.getType().name())
                                )
                        );
                    }
                }
            }
        }
    }
}
