package fr.skytorstd.moxed.manager.listeners;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.ItemManager;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ClickListeners implements Listener {
    public Main main;
    public ClickListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Inventory modInv = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();

        if(current == null) return;

        if(e.getView().getTitle().contains(" > Moderation")){
            if(Main.Staff.contains(p)){
                String targetName = e.getView().getTitle();
                targetName = targetName.substring(0, targetName.length()-13);
                Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                if(targetPlayer == null){
                    p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                }
                if(current.getType() == Material.PAPER){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§fDirect Message")){
                        e.setCancelled(true);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Faite la commande : §7/dm " + targetPlayer.getName() + " <votre_message>");
                        p.closeInventory();
                    }
                }

                if(current.getType() == Material.NETHERITE_AXE){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§cBan")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + targetPlayer.getName() + " §cVous avez été §9Banni(e) §cpar un membre du Staff !");
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetName + " §fvient d'être §9Banni(e)");
                            p.closeInventory();
                        }
                    }
                }

                if(current.getType() == Material.PAPER){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§9Mute")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            if(Main.Mute.contains(targetPlayer.getName())){
                                Main.Mute.remove(targetPlayer.getName());
                                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §fvient d'être §9Unmute");
                            }else {
                                Main.Mute.add(targetPlayer);
                                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §fvient d'être §9Mute");
                            }
                        }
                    }
                }

                if(current.getType() == Material.ANVIL){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§9Warns")){
                        e.setCancelled(true);
                        p.closeInventory();
                        p.chat("/warn " + targetPlayer.getName());
                    }
                }

                if(current.getType() == Material.IRON_SWORD){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Kick")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            targetPlayer.kickPlayer(Messages.PREFIX_ERRROR.getMessage() + "Vous avez été Kick par un membre du Staff !");
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §fvient d'être §9Kick");
                            p.closeInventory();
                        }
                    }
                }

                if(current.getType() == Material.COBBLESTONE){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§8GM 0")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            targetPlayer.setGameMode(GameMode.SURVIVAL);
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §6" + targetPlayer.getDisplayName() + " §fa été défini sur §9SURVIVAL");
                            targetPlayer.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9SURVIVAL");
                        }
                    }
                }

                if(current.getType() == Material.GRASS_BLOCK){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§2GM 1")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            targetPlayer.setGameMode(GameMode.CREATIVE);
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §6" + targetPlayer.getDisplayName() + " §fa été défini sur §9CREATIVE");
                            targetPlayer.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9CREATIVE");
                        }
                    }
                }

                if(current.getType() == Material.SPRUCE_LOG){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§cGM 2")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            targetPlayer.setGameMode(GameMode.ADVENTURE);
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §6" + targetPlayer.getDisplayName() + " §fa été défini sur §9ADVENTURE");
                            targetPlayer.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9ADVENTURE");
                        }
                    }
                }

                if(current.getType() == Material.BARRIER){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§fGM 3")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            targetPlayer.setGameMode(GameMode.SPECTATOR);
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §6" + targetPlayer.getDisplayName() + " §fa été défini sur §9SPECTATOR");
                            targetPlayer.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9SPECTATOR");
                        }
                    }
                }

                if(current.getType() == Material.ENDER_PEARL){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§fGoto")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            p.teleport(targetPlayer.getLocation());
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de te téléporter sur §6" + targetPlayer.getName());
                            p.closeInventory();
                        }
                    }
                }

                if(current.getType() == Material.ENDER_EYE){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§fBring")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            targetPlayer.teleport(p.getLocation());
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de téléporter §6" + targetPlayer.getName() + " §fsur toi");
                            p.closeInventory();
                        }
                    }
                }

                if(current.getType() == Material.GLASS){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§fVanish")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            if(targetPlayer.isInvisible() == true){
                                targetPlayer.setInvisible(false);
                                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de passer §6" + targetPlayer.getName() + " §fen §9Visible");
                            }else {
                                targetPlayer.setInvisible(true);
                                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de passer §6" + targetPlayer.getName() + " §fen §9Invisible");
                            }
                        }
                    }
                }

                if(current.getType() == Material.BEDROCK){
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§9Salle de Modération")){
                        if(Main.Staff.contains(p)){
                            e.setCancelled(true);
                            String location = main.getConfig().getString("modspace");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + p.getName() + " " + location);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + targetPlayer.getName() + " " + location);
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de téléporter §6" + targetPlayer.getName() + " §fest toi-même à la §9Salle de Modération");
                            p.closeInventory();
                        }
                    }
                }
            }else {
                p.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
            }
        }

        if(e.getView().getTitle().contains(" > Warns")){
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
            if(Main.Staff.contains(p)){
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
            if(Main.Staff.contains(p)){
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
        }

    }
}
