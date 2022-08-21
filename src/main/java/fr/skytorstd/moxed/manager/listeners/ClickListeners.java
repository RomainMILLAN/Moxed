package fr.skytorstd.moxed.manager.listeners;

import fr.skytorstd.moxed.Main;
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

        if(e.getView().getTitle().equalsIgnoreCase("Tablist")){
            if(Main.administrateur.contains(p) || Main.responsables.contains(p) || Main.moderateur.contains(p) || p.isOp()){
                if(current.getType() == Material.PLAYER_HEAD){
                    String targetPlayer = current.getItemMeta().getDisplayName();
                    e.setCancelled(true);
                    p.closeInventory();
                    p.chat("/tablist " + targetPlayer);
                }
            }else {
                e.setCancelled(true);
                p.closeInventory();
            }
        }

        if(current.getType() == Material.PAPER){
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§fDirect Message")){
                e.setCancelled(true);
                String targetName = e.getView().getTitle();
                Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                if(targetPlayer == null){
                    p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                }
                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Faite la commande : §7/dm " + targetPlayer.getName() + " <votre_message>");
                p.closeInventory();
            }
        }

        if(current.getType() == Material.NETHERITE_AXE){
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§cBan")){
                if(Main.Staff.contains(p)){
                    e.setCancelled(true);
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Kick")){
                if(Main.Staff.contains(p)){
                    e.setCancelled(true);
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
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
                    String targetName = e.getView().getTitle();
                    Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                    }
                    String location = main.getConfig().getString("modspace");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + p.getName() + " " + location);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + targetPlayer.getName() + " " + location);
                    p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de téléporter §6" + targetPlayer.getName() + " §fest toi-même à la §9Salle de Modération");
                    p.closeInventory();
                }
            }
        }

    }
}
