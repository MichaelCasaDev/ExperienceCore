package me.michaeldevc.experiencecore.Modules;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Emojis implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(event.getPlayer().hasPermission("emoji.place")){
            if(event.getMessage().contains(":sun:")){
                event.setMessage(event.getMessage().replace(":sun:", "☀"));
            } else if(event.getMessage().contains(":cloud:")){
                event.setMessage(event.getMessage().replace(":cloud:", "☁"));
            } else if(event.getMessage().contains(":umbrella:")){
                event.setMessage(event.getMessage().replace(":umbrella:", "☂"));
            } else if(event.getMessage().contains(":snowman:")){
                event.setMessage(event.getMessage().replace(":snowman:", "☃"));
            } else if(event.getMessage().contains(":comet:")){
                event.setMessage(event.getMessage().replace(":comet:", "☄"));
            } else if(event.getMessage().contains(":star:")){
                event.setMessage(event.getMessage().replace(":star:", "★"));
            } else if(event.getMessage().contains(":lightning:")){
                event.setMessage(event.getMessage().replace(":lightning:", "☇"));
            } else if(event.getMessage().contains(":thunder:")){
                event.setMessage(event.getMessage().replace(":thunder:", "☈"));
            } else if(event.getMessage().contains(":sun2:")){
                event.setMessage(event.getMessage().replace(":sun2:", "☉"));
            } else if(event.getMessage().contains(":conjuction:")){
                event.setMessage(event.getMessage().replace(":conjuction:", "☌"));
            } else if(event.getMessage().contains(":opposition:")){
                event.setMessage(event.getMessage().replace(":opposition:", "☍"));
            } else if(event.getMessage().contains(":telephone:")){
                event.setMessage(event.getMessage().replace(":telephone:", "☏"));
            } else if(event.getMessage().contains(":check:")){
                event.setMessage(event.getMessage().replace(":check:", "☑"));
            } else if(event.getMessage().contains(":x:")){
                event.setMessage(event.getMessage().replace(":x:", "☓"));
            } else if(event.getMessage().contains(":umbrella2:")){
                event.setMessage(event.getMessage().replace(":umbrella2:", "☔"));
            } else if(event.getMessage().contains(":coffee:")){
                event.setMessage(event.getMessage().replace(":coffee:", "☕"));
            } else if(event.getMessage().contains(":shamrock:")){
                event.setMessage(event.getMessage().replace(":shamrock:", "☘"));
            } else if(event.getMessage().contains(":left:")){
                event.setMessage(event.getMessage().replace(":left:", "☚"));
            } else if(event.getMessage().contains(":right:")){
                event.setMessage(event.getMessage().replace(":right:", "☛"));
            } else if(event.getMessage().contains(":up:")){
                event.setMessage(event.getMessage().replace(":up:", "☝"));
            } else if(event.getMessage().contains(":down:")){
                event.setMessage(event.getMessage().replace(":down:", "☟"));
            } else if(event.getMessage().contains(":skull:")){
                event.setMessage(event.getMessage().replace(":skull:", "☠"));
            } else if(event.getMessage().contains(":radioactive:")){
                event.setMessage(event.getMessage().replace(":radioactive:", "☢"));
            } else if(event.getMessage().contains(":biohazard:")){
                event.setMessage(event.getMessage().replace(":biohazard:", "☣"));
            } else if(event.getMessage().contains(":smile:")){
                event.setMessage(event.getMessage().replace(":smile:", "☺"));
            } else if(event.getMessage().contains(":frowning:")){
                event.setMessage(event.getMessage().replace(":frowning:", "☹"));
            } else if(event.getMessage().contains(":moon:")){
                event.setMessage(event.getMessage().replace(":moon:", "☽"));
            } else if(event.getMessage().contains(":moon2:")){
                event.setMessage(event.getMessage().replace(":moon2:", "☾"));
            } else if(event.getMessage().contains(":king:")){
                event.setMessage(event.getMessage().replace(":king:", "♔"));
            } else if(event.getMessage().contains(":queen:")){
                event.setMessage(event.getMessage().replace(":queen:", "♕"));
            } else if(event.getMessage().contains(":rook:")){
                event.setMessage(event.getMessage().replace(":rook:", "♖"));
            } else if(event.getMessage().contains(":bishop:")){
                event.setMessage(event.getMessage().replace(":bishop:", "♗"));
            } else if(event.getMessage().contains(":knight:")){
                event.setMessage(event.getMessage().replace(":knight:", "♘"));
            } else if(event.getMessage().contains(":pawn:")){
                event.setMessage(event.getMessage().replace(":pawn:", "♙"));
            } else if(event.getMessage().contains(":spade:")){
                event.setMessage(event.getMessage().replace(":spade:", "♠"));
            } else if(event.getMessage().contains(":heart:")){
                event.setMessage(event.getMessage().replace(":heart:", "♥"));
            } else if(event.getMessage().contains(":suit:")){
                event.setMessage(event.getMessage().replace(":suit:", "♣"));
            } else if(event.getMessage().contains(":diamondsuit:")){
                event.setMessage(event.getMessage().replace(":diamondsuit:", "♦"));
            } else if(event.getMessage().contains(":music:")){
                event.setMessage(event.getMessage().replace(":music:", "♫"));
            } else if(event.getMessage().contains(":recycling:")){
                event.setMessage(event.getMessage().replace(":recycling:", "♻"));
            } else if(event.getMessage().contains(":wheelchair:")){
                event.setMessage(event.getMessage().replace(":wheelchair:", "♿"));
            }
        }
    }
}