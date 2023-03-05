package fr.romainmillan.moxed.listeners;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.manager.ItemManager;
import fr.romainmillan.moxed.messages.ModerationMessages;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.object.Ticket;
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
            e.setCancelled(true);
            
            if (current.getType() == Material.BARRIER) {
                p.closeInventory();
            }

            if(current.getType() == Material.PAPER){
                p.chat("/cc clear");
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
            String targetName = e.getView().getTitle().split(" | ")[2];
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

        if(e.getView().getTitle().equals("Tickets")){
            if(current.getType() == Material.ANVIL){
                int ticketId = Integer.parseInt(current.getItemMeta().getDisplayName().split(" > ")[0]);
                Inventory ticketInventory = Bukkit.createInventory(null, 1*9, "Ticket > " + ticketId);

                ticketInventory.setItem(0, (ItemStack) ItemManager.craftItem(Material.BARRIER, "§cReturn"));
                ticketInventory.setItem(3, (ItemStack) ItemManager.craftItem(Material.ANVIL, current.getItemMeta().getDisplayName().split(" > ")[1]));
                ticketInventory.setItem(5, (ItemStack) ItemManager.craftItem(Material.PAPER, "§9Modération"));
                ticketInventory.setItem(8, (ItemStack) ItemManager.craftItem(Material.GREEN_DYE, "§aClose"));

                ticketInventory.setItem(1, (ItemStack) ItemManager.craftItemNone());
                ticketInventory.setItem(2, (ItemStack) ItemManager.craftItemNone());
                ticketInventory.setItem(4, (ItemStack) ItemManager.craftItemNone());
                ticketInventory.setItem(6, (ItemStack) ItemManager.craftItemNone());
                ticketInventory.setItem(7, (ItemStack) ItemManager.craftItemNone());

                p.openInventory(ticketInventory);
            }
        }

        if(e.getView().getTitle().startsWith("Ticket > ")){
            if(current.getType() == Material.GREEN_DYE){
                int ticketId = Integer.parseInt(e.getView().getTitle().substring(9));

                for(Ticket ticket : Main.tickets){
                    if(ticket.getId() == ticketId){
                        Main.tickets.remove(ticket);
                        break;
                    }
                }
               
                p.closeInventory();
                p.sendMessage(ModerationMessages.TICKET_DELETE_SUCESS.getMessages());
            }

            if(current.getType() == Material.BARRIER){
                p.chat("/ticket");
            }

            if(current.getType() == Material.PAPER){
                int ticketId = Integer.parseInt(e.getView().getTitle().substring(9));

                for(Ticket ticket : Main.tickets){
                    if(ticket.getId() == ticketId){
                        p.chat("/mm " + ticket.getAuthor());
                    }
                }

            }
        }

    }
}
