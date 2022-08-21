package fr.skytorstd.moxed.manager.listeners;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEventListeners implements Listener {
    public Main main;
    public JoinQuitEventListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        FileConfiguration RankerPlayerRank = YamlConfiguration.loadConfiguration(main.getFile("RankerPlayerRank"));
        if(RankerPlayerRank.contains("Players." + p.getName())){
            String playerGroupe = String.valueOf(RankerPlayerRank.get("Players." + p.getName()));

            if(playerGroupe.equalsIgnoreCase("administrateur")){
                if(!Main.administrateur.contains(p)){
                    Main.administrateur.add(p);
                }
            }else if(playerGroupe.equalsIgnoreCase("responsable")){
                if(!Main.responsables.contains(p)){
                    Main.responsables.add(p);
                }
            }else if(playerGroupe.equalsIgnoreCase("moderateur")){
                if(!Main.moderateur.contains(p)){
                    Main.moderateur.add(p);
                }
            }
        }

        if(Main.MAINTENANCE == true){
            if(!Main.administrateur.contains(p) && !Main.responsables.contains(p) && !Main.moderateur.contains(p)){
                e.setJoinMessage(" ");
                p.kickPlayer(Messages.PREFIX_ERRROR.getMessage() + "Le serveur est actuellement en maintenance.");
            }else {
                e.setJoinMessage(Messages.PREFIX_NORMAL.getMessage() + " §6" + p.getName() + " §7vient de se connecter !");
            }
        }else {
            e.setJoinMessage(Messages.PREFIX_NORMAL.getMessage() + " §6" + p.getName() + " §7vient de se connecter !");
        }

    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        if(Main.administrateur.contains(p)){
            Main.administrateur.remove(p);
        }
        if(Main.responsables.contains(p)){
            Main.responsables.remove(p);
        }
        if(Main.moderateur.contains(p)){
            Main.moderateur.remove(p);
        }

        e.setQuitMessage(Messages.PREFIX_NORMAL.getMessage() + " §6" + p.getName() + " §7vient de se déconnecter !");
    }


}
