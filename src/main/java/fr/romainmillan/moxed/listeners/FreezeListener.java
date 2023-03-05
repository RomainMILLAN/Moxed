package fr.romainmillan.moxed.listeners;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.ModerationMessages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {
    private Main main;
    public FreezeListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        if(Main.freezePlayer.contains(e.getPlayer())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ModerationMessages.PLAYER_FREEZE.getMessages());
        }

    }
}
