package fr.skytorstd.moxed.manager.listeners;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ModiListeners implements Listener {
    public Main main;
    public ModiListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();

        if(Main.Freeze.contains(p)){
            e.setCancelled(true);
            p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Tu a était freeze par un membre du staff");
        }
    }
}
