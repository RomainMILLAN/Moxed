package fr.skytorstd.moxed.manager.listeners;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListeners implements Listener {
    public Main main;
    public ChatListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(Main.Mute.contains(p)){
            e.setCancelled(true);
            p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Vous avez était mute par un membre du Staff");
        }

        if(Main.administrateur.contains(p)){
            e.setFormat(main.getConfig().getString("Ranker.administrateur.messages") + p.getName() + main.getConfig().getString("Ranker.messagesend") + e.getMessage());
        }else if(Main.responsables.contains(p)){
            e.setFormat(main.getConfig().getString("Ranker.responsable.messages") + p.getName() + main.getConfig().getString("Ranker.messagesend") + e.getMessage());
        }else if(Main.moderateur.contains(p)){
            e.setFormat(main.getConfig().getString("Ranker.moderateur.messages") + p.getName() + main.getConfig().getString("Ranker.messagesend") + e.getMessage());
        }else {
            e.setFormat(main.getConfig().getString("Ranker.joueur.messages") + p.getName() + main.getConfig().getString("Ranker.messagesend") + e.getMessage());
        }

    }
}
