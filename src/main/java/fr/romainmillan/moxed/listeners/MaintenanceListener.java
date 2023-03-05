package fr.romainmillan.moxed.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.ModerationMessages;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.RankerService;

public class MaintenanceListener implements Listener {
    private Main main;

    public MaintenanceListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(main.Maintenance == true){
            if(RankerService.isAdmin(e.getPlayer()) || RankerService.isResponsable(e.getPlayer()) || RankerService.isModerator(e.getPlayer())){
                e.getPlayer().sendMessage(MoxedMessage.PREFIX_STAFF.getMessage() + "Le serveur est en maintenance !");
            }else {
                e.getPlayer().kickPlayer(ModerationMessages.MAINTENANCE_ACTIVATE.getMessages());
                return;
            }
        }

        e.setJoinMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + RankerService.getStringPlayerRank(e.getPlayer(), main) + " §fvient de §9rejoindre §fle serveur");
    }

}
