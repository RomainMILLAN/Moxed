package fr.skytorstd.moxed.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class PlayersServerManager {
    public static void kickAllPlayers(){
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for(Player player : players){
            player.kickPlayer(Messages.MESSAGE_ENTREE_EN_MAINTENANCE.getMessage());
        }
    }
}
