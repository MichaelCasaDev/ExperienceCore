package me.michaeldevc.experiencecore.Modules;

import com.eazyftw.ezcolors.EZColors;
import me.clip.deluxechat.DeluxeChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class ChatUtils implements Listener, CommandExecutor {
    Configuration config = Bukkit.getServer().getPluginManager().getPlugin("ExperienceCore").getConfig();

    @EventHandler
    public void onPLayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();


        //Mention other users in the chat
        if(player.hasPermission("experiencecore.chat.use")){
            for (Player playerRecive : Bukkit.getOnlinePlayers()) {
                if (message.contains(playerRecive.getName()) && message.matches(".*\\b" + playerRecive.getName() + "\\b.*")) {
                    if(EZColors.getStorage().getUser(player.getUniqueId()).getColor() != null){
                        message = message.replace(playerRecive.getName(), ChatColor.translateAlternateColorCodes('&', config.getString("Chat.Default.Prefix") + playerRecive.getName() + "&" + EZColors.getStorage().getUser(player.getUniqueId()).getColor()));
                    } else {
                        message = message.replace(playerRecive.getName(), ChatColor.translateAlternateColorCodes('&', config.getString("Chat.Default.Prefix") + playerRecive.getName() + ChatColor.translateAlternateColorCodes('&', config.getString("Chat.No_Color"))));
                    }

                    if(config.getBoolean("Chat.Default.Audio.enable")){
                        playerRecive.playSound(playerRecive.getLocation(), Sound.valueOf(config.getString("Chat.Default.Audio.sound")), Float.parseFloat(config.getString("Chat.Default.Audio.volume")), Float.parseFloat(config.getString("Chat.Default.Audio.pitch")));
                    }

                    event.setMessage(message);
                }
            }
        }

        //Make an @everyone in the chat (mention all players)
        if (player.hasPermission("experiencecore.chat.everyone") && message.contains("@everyone")) {
            if(EZColors.getStorage().getUser(player.getUniqueId()).getColor() != null){
                message = message.replace("@everyone", ChatColor.translateAlternateColorCodes('&', config.getString("Chat.Everyone.Prefix") + "everyone" + ChatColor.translateAlternateColorCodes('&', "&" + EZColors.getStorage().getUser(player.getUniqueId()).getColor())));
            } else {
                message = message.replace("@everyone", ChatColor.translateAlternateColorCodes('&', config.getString("Chat.Everyone.Prefix") + "everyone" + ChatColor.translateAlternateColorCodes('&', config.getString("Chat.No_Color"))));
            }

            (new BukkitRunnable() {
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if(config.getBoolean("Chat.Everyone.Audio.enable")){
                            p.playSound(p.getLocation(), Sound.valueOf(config.getString("Chat.Everyone.Audio.sound")), Float.parseFloat(config.getString("Chat.Everyone.Audio.volume")), Float.parseFloat(config.getString("Chat.Everyone.Audio.pitch")));
                        }
                    }
                }
            }).runTaskLater(Bukkit.getPluginManager().getPlugin("ExperienceCore"), 0L);

            event.setMessage(message);
        }
    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event){ // Use this for /MSG because DeluxeChat is a shit :/
        String command = event.getMessage();
        Player p = event.getPlayer();

        if(command.startsWith("/msg")){

            event.setCancelled(true);

            String messaggio = "";
            String[] args = command.split(" ");

            if (args.length == 1) { // No player found
                p.getPlayer().performCommand("deluxechat:msg");
            } else {
                Player receiver = Bukkit.getPlayer(args[1]); // Set player to receive the message

                for (int i = 2; i < args.length; i++){
                    messaggio = messaggio + args[i] + " ";
                }


                if (!messaggio.equals("") && receiver != p && receiver != null) {
                    p.getPlayer().performCommand("deluxechat:msg " + receiver.getName() + " " + messaggio);

                    sendSound( // Send message to the player that receive the message
                            receiver, config.getString("Chat.R.Audio.sound"), config.getInt("Chat.R.Audio.volume"), config.getInt("Chat.R.Audio.pitch")
                    );
                } else {
                    p.getPlayer().performCommand("deluxechat:msg");
                }
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;

            /*
            if (commandLabel.equalsIgnoreCase("msg") || commandLabel.equalsIgnoreCase("w") || commandLabel // /msg <player> <message>
                    .equalsIgnoreCase("m") || commandLabel.equalsIgnoreCase("t") || commandLabel
                    .equalsIgnoreCase("pm") || commandLabel.equalsIgnoreCase("emsg") || commandLabel
                    .equalsIgnoreCase("epm") || commandLabel.equalsIgnoreCase("tell") || commandLabel
                    .equalsIgnoreCase("etell") || commandLabel.equalsIgnoreCase("whisper") || commandLabel
                    .equalsIgnoreCase("ewhisper")) {
                String messaggio = "";

                p.sendMessage("as08idyfhap9sduh8a9idfyuhgpisdyfuhgosi9dfugho");
                if (args.length == 0) { // No player found
                    p.getPlayer().performCommand("deluxechat:msg");
                    return true;
                }


                Player receiver = Bukkit.getPlayer(args[0]); // Set player to receive the message

                for (int i = 1; i < args.length; i++){
                    messaggio = messaggio + args[i] + " ";
                }



                if (messaggio != "" && receiver != p && receiver != null) {
                    p.getPlayer().performCommand("deluxechat:msg " + receiver.getName() + " " + messaggio);

                    p.sendMessage(receiver.getName());

                    sendSound( // Send message to the player that receive the message
                            receiver, config.getString("Chat.R.Audio.sound"), config.getInt("Chat.R.Audio.volume"), config.getInt("Chat.R.Audio.pitch")
                    );
                } else {
                    p.getPlayer().performCommand("deluxechat:msg");
                }
                return true;
            } else */if (commandLabel.equalsIgnoreCase("r")) { // /r <message>
                Player target = Bukkit.getPlayer(DeluxeChat.getPmRecipient(p.getName()));
                StringBuilder sb = new StringBuilder();

                if(target != null){
                    for (int i = 0; i < args.length; i++){ // Make all args into a single message string
                        sb.append(args[i] + " ");
                    }


                    p.getPlayer().performCommand("deluxechat:r " + sb.toString());

                    sendSound( // Send message to the player that receive the message
                            target, config.getString("Chat.R.Audio.sound"), config.getInt("Chat.R.Audio.volume"), config.getInt("Chat.R.Audio.pitch")
                    );
                } else {
                    p.getPlayer().performCommand("deluxechat:r");
                }
                return true;
            } else if (commandLabel.equalsIgnoreCase("tpa") && args.length > 0) { // /tpa <player>
                Player ricevitore = Bukkit.getPlayer(args[0]);
                p.performCommand("essentials:tpa " + args[0]);


                if (ricevitore != null && !ricevitore.getDisplayName().equals(p.getDisplayName())) {
                    sendSound( // Send the sound to the player that receive the request
                            ricevitore, config.getString("Chat.Tpa.Audio.sound"), config.getInt("Chat.Tpa.Audio.volume"), config.getInt("Chat.Tpa.Audio.pitch")
                    );
                }
            } else if (commandLabel.equalsIgnoreCase("tpahere") && args.length > 0) {
                Player ricevitore = Bukkit.getPlayer(args[0]);
                p.performCommand("essentials:tpahere " + args[0]);


                if (ricevitore != null && !ricevitore.getDisplayName().equals(p.getDisplayName())) {
                    sendSound( // Send the sound to the player that receive the request
                            ricevitore, config.getString("Chat.Tpa.Audio.sound"), config.getInt("Chat.Tpa.Audio.volume"), config.getInt("Chat.Tpa.Audio.pitch")
                    );
                }
            }

            return true;
        }

        return false;
    }

    public static void sendSound(Player receiver, String suono, float volume, float pitch) {
        receiver.playSound(receiver.getLocation(), Sound.valueOf(suono), volume, pitch);
    }
}
