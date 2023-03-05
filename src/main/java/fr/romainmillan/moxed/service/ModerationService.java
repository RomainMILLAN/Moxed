package fr.romainmillan.moxed.service;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.manager.ItemManager;
import fr.romainmillan.moxed.messages.MoxedMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModerationService {

    /*
     * MM
     */
    /**
     * Permet de téléporter une personne sur une autre et d'envoyer un message de confirmation
     * <pre/>
     *
     * @param targetPlayer
     * @param player
     * @param main
     */
    public static void bringTargetToPlayer(Player targetPlayer, Player player, Main main){
        targetPlayer.teleport(player.getLocation());
        player.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Tu viens de téléporter " + RankerService.getStringPlayerRank(targetPlayer, main) + " §fsur toi");
    }

    /**
     * Permet de téléporter le joueur player sur le joueur target et d'envoyer un message
     * <pre/>
     *
     * @param targetPlayer
     * @param player
     * @param main
     */
    public static void gotoPlayerToTarget(Player player, Player targetPlayer, Main main){
        player.teleport(targetPlayer.getLocation());
        player.sendMessage(MoxedMessage .PREFIX_NORMAL.getMessage() + "Tu viens de te téléporter sur " + RankerService.getStringPlayerRank(targetPlayer, main));
    }

    /**
     * Permet de vanish ou devanish un player target passer en paramètre
     * <pre/>
     *
     * @param player
     * @param targetPlayer
     * @param main
     */
    public static void vanishTarget(Player player, Player targetPlayer, Main main){
        if(targetPlayer.isInvisible() == true){
            targetPlayer.setInvisible(false);
            player.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Tu viens de passer " + RankerService.getStringPlayerRank(targetPlayer, main) + " §fen §9Visible");
        }else {
            targetPlayer.setInvisible(true);
            player.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Tu viens de passer " + RankerService.getStringPlayerRank(targetPlayer, main) + " §fen §9Invisible");
        }
    }

    public static void gamemodeToTargetPlayer(GameMode gm, Player player, Player targetPlayer, Main main){

    }

    /**
     * Crée l'inventaire de modération
     * <pre/>
     *
     * @param targetPlayer
     * @param main
     * @return
     */
    public static Inventory createModerationInventory(Player targetPlayer, Main main){
        Inventory moderationInventory = Bukkit.createInventory(null, 1*9, targetPlayer.getName() + " > Moderation");

        moderationInventory.setItem(0, (ItemStack) ItemManager.craftItem(Material.PAPER, RankerService.getStringPlayerRank(targetPlayer, main)));

        moderationInventory.setItem(6, (ItemStack) ItemManager.craftItem(Material.CHEST, "Utils"));
        moderationInventory.setItem(7, (ItemStack) ItemManager.craftItem(Material.NETHERITE_SWORD, "Sanctions"));
        moderationInventory.setItem(8, (ItemStack) ItemManager.craftItem(Material.GRASS_BLOCK, "Gamemode"));

        moderationInventory.setItem(1, (ItemStack) ItemManager.craftItemNone());
        moderationInventory.setItem(2, (ItemStack) ItemManager.craftItemNone());
        moderationInventory.setItem(3, (ItemStack) ItemManager.craftItemNone());
        moderationInventory.setItem(4, (ItemStack) ItemManager.craftItemNone());
        moderationInventory.setItem(5, (ItemStack) ItemManager.craftItemNone());

        return moderationInventory;
    }

    /**
     * Crée l'inventaire utile du gui de moderation
     * <pre/>
     *
     * @param targetPlayer
     * @return
     */
    public static Inventory createUtilsInventory(Player targetPlayer){
        Inventory moderationUtilsInventory = Bukkit.createInventory(null, 1*9, targetPlayer.getName() + " > Moderation > Utils");

        moderationUtilsInventory.setItem(0, (ItemStack) ItemManager.craftItem(Material.BARRIER, "§cReturn"));
        moderationUtilsInventory.setItem(3, (ItemStack) ItemManager.craftItem(Material.ENDER_PEARL, "§fGoto"));
        moderationUtilsInventory.setItem(4, (ItemStack) ItemManager.craftItem(Material.GLASS, "§fVanish"));
        moderationUtilsInventory.setItem(5, (ItemStack) ItemManager.craftItem(Material.ENDER_EYE, "§fBring"));

        moderationUtilsInventory.setItem(1, (ItemStack) ItemManager.craftItemNone());
        moderationUtilsInventory.setItem(2, (ItemStack) ItemManager.craftItemNone());
        moderationUtilsInventory.setItem(6, (ItemStack) ItemManager.craftItemNone());
        moderationUtilsInventory.setItem(7, (ItemStack) ItemManager.craftItemNone());
        moderationUtilsInventory.setItem(8, (ItemStack) ItemManager.craftItemNone());

        return moderationUtilsInventory;
    }

    /**
     * Crée l'inventaire des sacntions du gui de modération
     * <pre/>
     *
     * @param targetPlayer
     * @return
     */
    public static Inventory createSanctionsInventory(Player targetPlayer){
        Inventory moderationSanctionsInventory = Bukkit.createInventory(null, 1*9, targetPlayer.getName() + " > Moderation > Sanctions");

        moderationSanctionsInventory.setItem(0, (ItemStack) ItemManager.craftItem(Material.BARRIER, "§cReturn"));
        moderationSanctionsInventory.setItem(5, (ItemStack) ItemManager.craftItem(Material.PAPER, "§9Mute"));
        moderationSanctionsInventory.setItem(6, (ItemStack) ItemManager.craftItem(Material.ANVIL, "§9Warns"));
        moderationSanctionsInventory.setItem(7, (ItemStack) ItemManager.craftItem(Material.IRON_SWORD, "§6Kick"));
        moderationSanctionsInventory.setItem(8, (ItemStack) ItemManager.craftItem(Material.NETHERITE_AXE, "§cBan"));

        moderationSanctionsInventory.setItem(1, (ItemStack) ItemManager.craftItemNone());
        moderationSanctionsInventory.setItem(2, (ItemStack) ItemManager.craftItemNone());
        moderationSanctionsInventory.setItem(3, (ItemStack) ItemManager.craftItemNone());
        moderationSanctionsInventory.setItem(4, (ItemStack) ItemManager.craftItemNone());

        return moderationSanctionsInventory;
    }

    /**
     * Crée l'inventaire du gamemode du gui de modération
     * <pre/>
     *
     * @param targetPlayer
     * @return
     */
    public static Inventory createGamemodeInventory(Player targetPlayer){
        Inventory moderationGamemodeInventory = Bukkit.createInventory(null, 1*9, targetPlayer.getName() + " > Moderation > GM");

        moderationGamemodeInventory.setItem(0, (ItemStack) ItemManager.craftItem(Material.BARRIER, "§cReturn"));
        moderationGamemodeInventory.setItem(5, (ItemStack) ItemManager.craftItem(Material.COBBLESTONE, "§8GM 0"));
        moderationGamemodeInventory.setItem(6, (ItemStack) ItemManager.craftItem(Material.GRASS_BLOCK, "§2GM 1"));
        moderationGamemodeInventory.setItem(7, (ItemStack) ItemManager.craftItem(Material.SPRUCE_LOG, "§cGM 2"));
        moderationGamemodeInventory.setItem(8, (ItemStack) ItemManager.craftItem(Material.GLASS_PANE, "§fGM 3"));

        moderationGamemodeInventory.setItem(1, (ItemStack) ItemManager.craftItemNone());
        moderationGamemodeInventory.setItem(2, (ItemStack) ItemManager.craftItemNone());
        moderationGamemodeInventory.setItem(3, (ItemStack) ItemManager.craftItemNone());
        moderationGamemodeInventory.setItem(4, (ItemStack) ItemManager.craftItemNone());

        return moderationGamemodeInventory;
    }
}
