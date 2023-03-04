package fr.romainmillan.moxed.listeners;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.state.Ranks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private Main main;
    public ChatListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){

        if(Main.rankPlayer.get(e.getPlayer()) == Ranks.ADMINISTRATEUR){

            e.setFormat(main.getConfig().getString("Ranker.administrateur.messages") + e.getPlayer().getName() + main.getConfig().getString("Ranker.messagesend") + e.getMessage());

        }else if(Main.rankPlayer.get(e.getPlayer()) == Ranks.RESPONSABLE){

            e.setFormat(main.getConfig().getString("Ranker.responsable.messages") + e.getPlayer().getName() + main.getConfig().getString("Ranker.messagesend") + e.getMessage());

        }else if(Main.rankPlayer.get(e.getPlayer()) == Ranks.MODERATEUR){

            e.setFormat(main.getConfig().getString("Ranker.moderateur.messages") + e.getPlayer().getName() + main.getConfig().getString("Ranker.messagesend") + e.getMessage());

        }else{

            e.setFormat(main.getConfig().getString("Ranker.joueur.messages") + e.getPlayer().getName() + main.getConfig().getString("Ranker.messagesend") + e.getMessage());

        }

    }
}
