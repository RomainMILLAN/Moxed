package fr.romainmillan.moxed.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.ModerationMessages;

public class MaintenanceListener implements Listener {
    private Main main;

    public MaintenanceListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(main.Maintenance == true){
            e.getPlayer().kickPlayer(ModerationMessages.MAINTENANCE_ACTIVATE.getMessages());
            return;
        }
    }

}
