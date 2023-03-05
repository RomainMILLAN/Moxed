package fr.romainmillan.moxed.listeners;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.ModerationMessages;
import fr.romainmillan.moxed.service.RankerService;
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

        if(Main.mutePlayer.contains(e.getPlayer())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ModerationMessages.MUTE.getMessages());
        }

        e.setFormat(RankerService.getStringPlayerMessage(e.getPlayer(), main) + e.getMessage());

    }
}
