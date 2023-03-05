package fr.romainmillan.moxed.listeners;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.manager.ItemManager;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.ModerationService;
import fr.romainmillan.moxed.service.RankerService;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ModerationListener implements Listener {
    private Main main;
    public ModerationListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();

        if(current == null) return;

        if(e.getView().getTitle().endsWith(" > Moderation")){
            if(RankerService.isInStaff(p)){
                String targetName = e.getView().getTitle();
                targetName = targetName.substring(0, targetName.length()-13);
                Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                if(targetPlayer == null){
                    p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                    return;
                }
                e.setCancelled(true);

                if(current.getType() == Material.CHEST){
                    p.closeInventory();
                    p.openInventory(ModerationService.createUtilsInventory(targetPlayer));
                    return;
                }

                if(current.getType() == Material.NETHERITE_SWORD){
                    p.closeInventory();
                    p.openInventory(ModerationService.createSanctionsInventory(targetPlayer));
                    return;
                }

                if(current.getType() == Material.GRASS_BLOCK){
                    if(RankerService.isAdmin(p) || RankerService.isResponsable(p)){
                        p.closeInventory();
                        p.openInventory(ModerationService.createGamemodeInventory(targetPlayer));
                        return;
                    }else {
                        p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                        p.closeInventory();
                        return;
                    }
                }

            }else {
                p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                p.closeInventory();
            }
        }

        if(e.getView().getTitle().endsWith(" > Moderation > Utils")){
            if(RankerService.isInStaff(p)){
                String targetName = e.getView().getTitle();
                targetName = targetName.substring(0, targetName.length()-21);
                Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                if(targetPlayer == null){
                    p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                    return;
                }
                e.setCancelled(true);

                if(current.getType() == Material.BARRIER){
                    p.closeInventory();
                    p.openInventory(ModerationService.createModerationInventory(targetPlayer, main));
                }

                if(current.getType() == Material.ENDER_PEARL){
                    ModerationService.gotoPlayerToTarget(p, targetPlayer, main);
                    return;
                }

                if(current.getType() == Material.GLASS){
                    ModerationService.vanishTarget(p, targetPlayer, main);
                    return;
                }

                if(current.getType() == Material.ENDER_EYE){
                    ModerationService.bringTargetToPlayer(targetPlayer, p, main);
                    return;
                }
            }else {
                p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                p.closeInventory();
            }
        }

        if(e.getView().getTitle().endsWith(" > Moderation > Sanctions")){
            String targetName = e.getView().getTitle();
            targetName = targetName.substring(0, targetName.length()-25);
            Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
            if(targetPlayer == null){
                p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                return;
            }
            e.setCancelled(true);

            if(current.getType() == Material.BARRIER){
                p.closeInventory();
                p.openInventory(ModerationService.createModerationInventory(targetPlayer, main));
            }

            if(current.getType() == Material.PAPER){

            }

            if(current.getType() == Material.ANVIL){

            }

            if(current.getType() == Material.BLUE_ICE){
                p.chat("/freeze " + targetPlayer.getName());
            }

            if(current.getType() == Material.IRON_SWORD){

            }

            if(current.getType() == Material.NETHERITE_AXE){

            }
        }

        if(e.getView().getTitle().endsWith(" > Moderation > GM")){
            String targetName = e.getView().getTitle();
            targetName = targetName.substring(0, targetName.length()-18);
            Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
            if(targetPlayer == null){
                p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                return;
            }
            e.setCancelled(true);

            if(current.getType() == Material.BARRIER){
                p.closeInventory();
                p.openInventory(ModerationService.createModerationInventory(targetPlayer, main));
            }

            if(current.getType() == Material.COBBLESTONE){
                p.chat("/gm " + targetPlayer.getName() + " 0");
            }

            if(current.getType() == Material.GRASS_BLOCK){
                p.chat("/gm " + targetPlayer.getName() + " 1");
            }

            if(current.getType() == Material.SPRUCE_LOG){
                p.chat("/gm " + targetPlayer.getName() + " 2");
            }

            if(current.getType() == Material.GLASS_PANE){
                p.chat("/gm " + targetPlayer.getName() + " 3");
            }
        }

        /*if(e.getView().getTitle().contains(" > Warns")){
            if(Main.administrateur.contains(p) || Main.responsables.contains(p) || Main.moderateur.contains(p) || p.isOp()){
                e.setCancelled(true);
                String playerName = e.getView().getTitle();
                playerName = playerName.substring(0, playerName.length()-8);
                Player targetPlayer = Bukkit.getServer().getPlayer(playerName);

                if(targetPlayer == null){
                    p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                    p.closeInventory();
                }


                if(current.getType() == Material.ANVIL){
                    Inventory warnInventory = Bukkit.createInventory(null, 36, targetPlayer.getName() + " > Warn > See");
                    System.out.println(current.getItemMeta().getDisplayName());

                    warnInventory.setItem(13, (ItemStack) ItemManager.craftItem(Material.ANVIL, e.getCurrentItem().getItemMeta().getDisplayName()));
                    warnInventory.setItem(21, (ItemStack) ItemManager.craftItem(Material.SNOWBALL, "Ferme"));
                    warnInventory.setItem(23, (ItemStack) ItemManager.craftItem(Material.RED_DYE, "Supprimer"));

                    p.closeInventory();
                    p.openInventory(warnInventory);
                }else {
                    e.setCancelled(true);
                    p.closeInventory();
                }
            }else {
                p.closeInventory();
            }
        }

        if(e.getView().getTitle().contains(" > Warn > See")){
            if(Main.administrateur.contains(p) || Main.responsables.contains(p) || Main.moderateur.contains(p) || p.isOp()){
                e.setCancelled(true);
                String playerName = e.getView().getTitle();
                playerName = playerName.substring(0, playerName.length()-13);
                Player targetPlayer = Bukkit.getServer().getPlayer(playerName);

                if(targetPlayer == null){
                    p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                    p.closeInventory();
                }
                String nameWarn = e.getInventory().getItem(13).getItemMeta().getDisplayName();
                String[] idWarnArgs = nameWarn.split("|");
                int idWarn = Integer.parseInt(idWarnArgs[0]);

                if(current.getType() == Material.SNOWBALL){
                    p.closeInventory();
                    p.chat("/warn " + playerName);
                }
                if(current.getType() == Material.RED_DYE){
                    p.closeInventory();
                    p.chat("/warn " + playerName + " remove " + idWarn);
                }
            }else{
                p.closeInventory();
            }
        }

        if(e.getView().getTitle().contains("Liste des Tickets")){
            if(Main.staffPlayer.contains(p)){
                e.setCancelled(true);

                if(current.getType() == Material.PAPER){
                    String ticketName = current.getItemMeta().getDisplayName();
                    String[] targetNameArgs = ticketName.split("->");
                    String targetName = targetNameArgs[0];
                    targetName = targetName.substring(0, targetName.length()-1);
                    ticketName = targetNameArgs[1].substring(1, targetNameArgs[1].length());
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                        p.closeInventory();
                    }

                    Inventory ticketInventory = Bukkit.createInventory(null, 45, "Ticket > " + targetPlayer.getName());

                    String ticket = current.getItemMeta().getDisplayName().substring(targetPlayer.getName().length()+3);
                    ticketInventory.setItem(13, (ItemStack) ItemManager.craftItem(Material.PAPER, ticket));
                    ticketInventory.setItem(19, (ItemStack) ItemManager.craftItem(Material.SNOWBALL, "Ferme"));
                    ticketInventory.setItem(21, (ItemStack) ItemManager.craftItem(Material.ENDER_PEARL, "Goto"));
                    ticketInventory.setItem(23, (ItemStack) ItemManager.craftItem(Material.ENDER_EYE, "Bring"));
                    ticketInventory.setItem(25, (ItemStack) ItemManager.craftItem(Material.RED_DYE, "Supprimer"));
                    ticketInventory.setItem(36, (ItemStack) ItemManager.craftItem(Material.DIAMOND_SWORD, "GUI de Modération"));
                    ticketInventory.setItem(44, (ItemStack) ItemManager.craftItem(Material.BEDROCK, "Salle de Modération"));

                    p.closeInventory();
                    p.openInventory(ticketInventory);
                }
            }else {
                p.closeInventory();
            }
        }

        if(e.getView().getTitle().contains("Ticket > ")){
            if(Main.staffPlayer.contains(p)){
                e.setCancelled(true);
                String playerName = e.getView().getTitle();
                playerName = playerName.substring(9, playerName.length());
                Player targetPlayer = Bukkit.getServer().getPlayer(playerName);
                String nameTicket = e.getInventory().getItem(13).getItemMeta().getDisplayName();

                if(targetPlayer == null){
                    p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                    p.closeInventory();
                }

                if(current.getType() == Material.SNOWBALL){
                    p.closeInventory();
                    p.chat("/ticket");
                }
                if(current.getType() == Material.ENDER_PEARL){
                    p.closeInventory();
                    p.teleport(targetPlayer.getLocation());
                    p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de te téléporter sur §6" + targetPlayer.getName());
                }
                if(current.getType() == Material.ENDER_EYE){
                    p.closeInventory();
                    targetPlayer.teleport(p.getLocation());
                    p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de téléporter §6" + targetPlayer.getName() + " §fsur toi");
                }
                if(current.getType() == Material.RED_DYE){
                    p.closeInventory();
                    Main.Tickets.remove(targetPlayer);
                    p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le §9Ticket §fa été supprimée");
                }
                if(current.getType() == Material.DIAMOND_SWORD){
                    p.closeInventory();
                    p.chat("/moderation " + targetPlayer.getName());
                }
                if(current.getType() == Material.BEDROCK){
                    p.closeInventory();
                    String location = main.getConfig().getString("modspace");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + p.getName() + " " + location);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + targetPlayer.getName() + " " + location);
                    p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de téléporter §6" + targetPlayer.getName() + " §fest toi-même à la §9Salle de Modération");
                }
            }else {
                p.closeInventory();
            }
        }*/

    }
}
