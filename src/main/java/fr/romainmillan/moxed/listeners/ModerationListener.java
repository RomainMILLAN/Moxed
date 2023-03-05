package fr.romainmillan.moxed.listeners;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.manager.ItemManager;
import fr.romainmillan.moxed.messages.ModerationMessages;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.ModerationService;
import fr.romainmillan.moxed.service.RankerService;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();

        if (current == null)
            return;

        if (e.getView().getTitle().endsWith(" > Moderation")) {
            if (RankerService.isInStaff(p)) {
                String targetName = e.getView().getTitle();
                targetName = targetName.substring(0, targetName.length() - 13);
                Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                if (targetPlayer == null) {
                    p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                    return;
                }
                e.setCancelled(true);

                if (current.getType() == Material.CHEST) {
                    p.closeInventory();
                    p.openInventory(ModerationService.createUtilsInventory(targetPlayer));
                    return;
                }

                if (current.getType() == Material.NETHERITE_SWORD) {
                    p.closeInventory();
                    p.openInventory(ModerationService.createSanctionsInventory(targetPlayer));
                    return;
                }

                if (current.getType() == Material.GRASS_BLOCK) {
                    if (RankerService.isAdmin(p) || RankerService.isResponsable(p)) {
                        p.closeInventory();
                        p.openInventory(ModerationService.createGamemodeInventory(targetPlayer));
                        return;
                    } else {
                        p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                        p.closeInventory();
                        return;
                    }
                }

            } else {
                p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                p.closeInventory();
            }
        }

        if (e.getView().getTitle().endsWith(" > Moderation > Utils")) {
            if (RankerService.isInStaff(p)) {
                String targetName = e.getView().getTitle();
                targetName = targetName.substring(0, targetName.length() - 21);
                Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                if (targetPlayer == null) {
                    p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                    return;
                }
                e.setCancelled(true);

                if (current.getType() == Material.BARRIER) {
                    p.closeInventory();
                    p.openInventory(ModerationService.createModerationInventory(targetPlayer, main));
                }

                if (current.getType() == Material.ENDER_PEARL) {
                    ModerationService.gotoPlayerToTarget(p, targetPlayer, main);
                    return;
                }

                if (current.getType() == Material.GLASS) {
                    ModerationService.vanishTarget(p, targetPlayer, main);
                    return;
                }

                if (current.getType() == Material.ENDER_EYE) {
                    ModerationService.bringTargetToPlayer(targetPlayer, p, main);
                    return;
                }
            } else {
                p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                p.closeInventory();
            }
        }

        if (e.getView().getTitle().endsWith(" > Moderation > Sanctions")) {
            String targetName = e.getView().getTitle();
            targetName = targetName.substring(0, targetName.length() - 25);
            Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
            if (targetPlayer == null) {
                p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                return;
            }
            e.setCancelled(true);

            if (current.getType() == Material.BARRIER) {
                p.closeInventory();
                p.openInventory(ModerationService.createModerationInventory(targetPlayer, main));
            }

            if (current.getType() == Material.PAPER) {
                p.chat("/cc mute " + targetPlayer.getName());
            }

            if (current.getType() == Material.ANVIL) {
                p.chat("/warns " + targetPlayer.getName());
            }

            if (current.getType() == Material.BLUE_ICE) {
                ModerationService.freezeTargetPlayer(p, targetPlayer, main);
            }

            if (current.getType() == Material.IRON_SWORD) {
                targetPlayer.kickPlayer("Vous venez d'être kick par " + RankerService.getStringPlayerRank(p, main));
                p.sendMessage(
                        ModerationMessages.KICK_PERSON.getMessages() + RankerService.getStringPlayerRank(p, main));
                p.closeInventory();
            }

            if (current.getType() == Material.NETHERITE_AXE) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + targetPlayer.getName()
                        + " Vous êtes bannie par " + RankerService.getStringPlayerRank(targetPlayer, main));
                p.sendMessage(ModerationMessages.BAN_PERSON.getMessages() + RankerService.getStringPlayerRank(p, main));
                p.closeInventory();
            }
        }

        if (e.getView().getTitle().endsWith(" > Moderation > GM")) {
            String targetName = e.getView().getTitle();
            targetName = targetName.substring(0, targetName.length() - 18);
            Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
            if (targetPlayer == null) {
                p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                return;
            }
            e.setCancelled(true);

            if (current.getType() == Material.BARRIER) {
                p.closeInventory();
                p.openInventory(ModerationService.createModerationInventory(targetPlayer, main));
            }

            if (current.getType() == Material.COBBLESTONE) {
                p.chat("/gm " + targetPlayer.getName() + " 0");
            }

            if (current.getType() == Material.GRASS_BLOCK) {
                p.chat("/gm " + targetPlayer.getName() + " 1");
            }

            if (current.getType() == Material.SPRUCE_LOG) {
                p.chat("/gm " + targetPlayer.getName() + " 2");
            }

            if (current.getType() == Material.GLASS_PANE) {
                p.chat("/gm " + targetPlayer.getName() + " 3");
            }
        }

        if (e.getView().getTitle().equals("Système")) {
            if (current.getType() == Material.BARRIER) {
                p.closeInventory();
            }

            if (current.getType() == Material.CONDUIT) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
                p.sendMessage(ModerationMessages.WEATHER_CLEAR.getMessages());
            }

            if (current.getType() == Material.RECOVERY_COMPASS) {
                p.chat("/night");
            }

            if (current.getType() == Material.CLOCK) {
                p.chat("/day");
            }

            if (current.getType() == Material.ANVIL) {
                p.sendMessage(MoxedMessage.PREFIX_ERROR.getMessage() + "IMPOSSIBLE");
                p.closeInventory();
            }

            if (current.getType() == Material.LIGHTNING_ROD) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
                p.closeInventory();
            }
        }

        if (e.getView().getTitle().endsWith(" > Warns")) {
            if (current.getType() == Material.ANVIL) {
                String targetName = e.getView().getTitle();
                targetName = targetName.substring(0, targetName.length() - 8);
                Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
                if (targetPlayer == null) {
                    p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                    return;
                }
                e.setCancelled(true);

                FileConfiguration warnsFile = YamlConfiguration.loadConfiguration(main.getFile("warns"));
                List<String> warnsListPlayer = warnsFile.getStringList(targetPlayer.getName());
                HashMap<Integer, String> warnsPlayerWithId = new HashMap<>();
                int i = 1;
                for (String str : warnsListPlayer) {
                    warnsPlayerWithId.put(i, str);
                    i++;
                }

                int IDWARN = Integer.parseInt(current.getItemMeta().getDisplayName().split("|")[0]);
                Inventory warnInventory = Bukkit.createInventory(null, 1 * 9, "Warn | " + targetPlayer.getName() + " | " + IDWARN);
                warnInventory.setItem(0, (ItemStack) ItemManager.craftItem(Material.BARRIER, "§cReturn"));
                warnInventory.setItem(4, (ItemStack) ItemManager.craftItem(Material.ANVIL, warnsPlayerWithId.get(IDWARN)));
                warnInventory.setItem(8, (ItemStack) ItemManager.craftItem(Material.NETHERITE_SWORD, "§cSupprimé"));

                warnInventory.setItem(1, (ItemStack) ItemManager.craftItemNone());
                warnInventory.setItem(2, (ItemStack) ItemManager.craftItemNone());
                warnInventory.setItem(3, (ItemStack) ItemManager.craftItemNone());
                warnInventory.setItem(5, (ItemStack) ItemManager.craftItemNone());
                warnInventory.setItem(6, (ItemStack) ItemManager.craftItemNone());
                warnInventory.setItem(7, (ItemStack) ItemManager.craftItemNone());

                p.openInventory(warnInventory);
            }
        }

        if(e.getView().getTitle().startsWith("Warn | ")){
            System.out.println(e.getView().getTitle());
            String targetName = e.getView().getTitle().split(" | ")[2];
            System.out.println(e.getView().getTitle().split(" | ")[2]);
            Player targetPlayer = Bukkit.getServer().getPlayer(targetName);
            if (targetPlayer == null) {
                p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                return;
            }
            e.setCancelled(true);

            int IDWARN = Integer.parseInt(e.getView().getTitle().split(" | ")[4]);

            if(current.getType() == Material.BARRIER){
                p.chat("/warns " + targetPlayer.getName());
            }

            if(current.getType() == Material.NETHERITE_SWORD){
                p.chat("/warns " + targetPlayer.getName() + " remove " + IDWARN);
                p.closeInventory();
            }
        }

         /* 
         * if(e.getView().getTitle().contains("Ticket > ")){
         * if(Main.staffPlayer.contains(p)){
         * e.setCancelled(true);
         * String playerName = e.getView().getTitle();
         * playerName = playerName.substring(9, playerName.length());
         * Player targetPlayer = Bukkit.getServer().getPlayer(playerName);
         * String nameTicket =
         * e.getInventory().getItem(13).getItemMeta().getDisplayName();
         * 
         * if(targetPlayer == null){
         * p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
         * p.closeInventory();
         * }
         * 
         * if(current.getType() == Material.SNOWBALL){
         * p.closeInventory();
         * p.chat("/ticket");
         * }
         * if(current.getType() == Material.ENDER_PEARL){
         * p.closeInventory();
         * p.teleport(targetPlayer.getLocation());
         * p.sendMessage(Messages.PREFIX_NORMAL.getMessage() +
         * "Tu viens de te téléporter sur §6" + targetPlayer.getName());
         * }
         * if(current.getType() == Material.ENDER_EYE){
         * p.closeInventory();
         * targetPlayer.teleport(p.getLocation());
         * p.sendMessage(Messages.PREFIX_NORMAL.getMessage() +
         * "Tu viens de téléporter §6" + targetPlayer.getName() + " §fsur toi");
         * }
         * if(current.getType() == Material.RED_DYE){
         * p.closeInventory();
         * Main.Tickets.remove(targetPlayer);
         * p.sendMessage(Messages.PREFIX_NORMAL.getMessage() +
         * "Le §9Ticket §fa été supprimée");
         * }
         * if(current.getType() == Material.DIAMOND_SWORD){
         * p.closeInventory();
         * p.chat("/moderation " + targetPlayer.getName());
         * }
         * if(current.getType() == Material.BEDROCK){
         * p.closeInventory();
         * String location = main.getConfig().getString("modspace");
         * Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + p.getName() +
         * " " + location);
         * Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " +
         * targetPlayer.getName() + " " + location);
         * p.sendMessage(Messages.PREFIX_NORMAL.getMessage() +
         * "Tu viens de téléporter §6" + targetPlayer.getName() +
         * " §fest toi-même à la §9Salle de Modération");
         * }
         * }else {
         * p.closeInventory();
         * }
         * }
         */

    }
}
