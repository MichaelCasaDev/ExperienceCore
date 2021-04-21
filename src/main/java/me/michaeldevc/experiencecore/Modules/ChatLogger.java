package me.michaeldevc.experiencecore.Modules;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class ChatLogger implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        String fileName = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ExperienceCore")).getDataFolder() + "/chat.log";
        Date dt = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(dt);
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        String dataString = "[" + hours + ":" + minutes + ":" + seconds + "] ";

        String string =  dataString + event.getPlayer().getName() + " - " + event.getMessage() + "\n";

        PrintWriter printWriter = null;
        File file = new File(fileName);
        try {
            if (!file.exists()) file.createNewFile();
            printWriter = new PrintWriter(new FileOutputStream(fileName, true));
            printWriter.write(string);
        } catch (IOException ioex) {
            //oww...
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }
}
