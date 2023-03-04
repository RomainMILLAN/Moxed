package fr.romainmillan.moxed.listeners;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.RankerService;
import fr.romainmillan.moxed.state.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class RankerListener implements Listener {
    public Main main;
    public RankerListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        String SUCCESS = MoxedMessage.PREFIX_NORMAL + "Vous venez de mettre le rôle ";

        if(e.getView().getTitle().contains("Ranker >")){
            if(p.isOp() || RankerService.isAdmin(p)){

                String targetName = e.getView().getTitle();
                targetName = targetName.substring(9, targetName.length());
                Player targetPlayer = Bukkit.getServer().getPlayer(targetName);

                if(targetPlayer == null){
                    p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                    p.closeInventory();
                    return;
                }

                e.setCancelled(true);

                if(current.getItemMeta().getDisplayName().equalsIgnoreCase(RankerService.getRanksWithRanksState(Ranks.ADMINISTRATEUR))){
                    RankerService.setRanksToPlayer(targetPlayer, Ranks.ADMINISTRATEUR, main);
                    p.closeInventory();
                    p.sendMessage(SUCCESS + RankerService.getRanksPlayerWithColor(targetPlayer) + " §fà §9" + targetPlayer.getName());
                    return;
                }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase(RankerService.getRanksWithRanksState(Ranks.RESPONSABLE))){
                    RankerService.setRanksToPlayer(targetPlayer, Ranks.RESPONSABLE, main);
                    p.closeInventory();
                    p.sendMessage(SUCCESS + RankerService.getRanksPlayerWithColor(targetPlayer) + " §fà §9" + targetPlayer.getName());
                    return;
                }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase(RankerService.getRanksWithRanksState(Ranks.MODERATEUR))){
                    RankerService.setRanksToPlayer(targetPlayer, Ranks.MODERATEUR, main);
                    p.closeInventory();
                    p.sendMessage(SUCCESS + RankerService.getRanksPlayerWithColor(targetPlayer) + " §fà §9" + targetPlayer.getName());
                    return;
                }else {
                    RankerService.setRanksToPlayer(targetPlayer, Ranks.JOUEUR, main);
                    p.closeInventory();
                    p.sendMessage(SUCCESS + RankerService.getRanksPlayerWithColor(targetPlayer) + " §fà §9" + targetPlayer.getName());
                    return;
                }
            }else {
                p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                return;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        FileConfiguration rankers = YamlConfiguration.loadConfiguration(main.getFile("ranker"));

        if(rankers.contains("Players." + e.getPlayer().getName())){

            String rankString = rankers.getString("Players." + e.getPlayer().getName());

            if(rankString.equalsIgnoreCase(Ranks.ADMINISTRATEUR.getRanks())){
                RankerService.setRankToPlayerCurrent(e.getPlayer(), Ranks.ADMINISTRATEUR, main);
            }else if(rankString.equalsIgnoreCase(Ranks.RESPONSABLE.getRanks())){
                RankerService.setRankToPlayerCurrent(e.getPlayer(), Ranks.RESPONSABLE, main);
            }else if(rankString.equalsIgnoreCase(Ranks.MODERATEUR.getRanks())){
                RankerService.setRankToPlayerCurrent(e.getPlayer(), Ranks.MODERATEUR, main);
            }else {
                RankerService.setRankToPlayerCurrent(e.getPlayer(), Ranks.JOUEUR, main);
            }

        }else {
            Main.rankPlayer.put(e.getPlayer(), Ranks.JOUEUR);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        if(Main.rankPlayer.containsKey(e.getPlayer())){
            Main.rankPlayer.remove(e.getPlayer());
        }

    }
}
