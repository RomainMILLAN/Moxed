package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.ItemManager;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class commandModeration implements CommandExecutor {
    private Main main;
    public commandModeration(Main main) {
        this.main=main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("moderation")){
            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
            }
            Player p = (Player) sender;

            if(Main.Staff.contains(p)){
                Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                if(targetPlayer == null){
                    p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                    return false;
                }

                //INV MODERATION
                int scale = 6*9;
                Inventory tablistInventoryModeration = Bukkit.createInventory(null, scale, targetPlayer.getName());

                tablistInventoryModeration.setItem(0, (ItemStack) ItemManager.craftModerationGUIItem(Material.PAPER, "§fDirect Message"));
                tablistInventoryModeration.setItem(10, (ItemStack) ItemManager.craftModerationGUIItem(Material.NETHERITE_AXE, "§cBan"));
                tablistInventoryModeration.setItem(13, (ItemStack) ItemManager.craftModerationGUIItem(Material.PAPER, "§9Mute"));
                tablistInventoryModeration.setItem(16, (ItemStack) ItemManager.craftModerationGUIItem(Material.ANVIL, "§6Kick"));
                tablistInventoryModeration.setItem(20, (ItemStack) ItemManager.craftModerationGUIItem(Material.COBBLESTONE, "§8GM 0"));
                tablistInventoryModeration.setItem(21, (ItemStack) ItemManager.craftModerationGUIItem(Material.GRASS_BLOCK, "§2GM 1"));
                tablistInventoryModeration.setItem(23, (ItemStack) ItemManager.craftModerationGUIItem(Material.SPRUCE_LOG, "§cGM 2"));
                tablistInventoryModeration.setItem(24, (ItemStack) ItemManager.craftModerationGUIItem(Material.BARRIER, "§fGM 3"));
                tablistInventoryModeration.setItem(30, (ItemStack) ItemManager.craftModerationGUIItem(Material.ENDER_PEARL, "§fGoto"));
                tablistInventoryModeration.setItem(32, (ItemStack) ItemManager.craftModerationGUIItem(Material.ENDER_EYE, "§fBring"));
                tablistInventoryModeration.setItem(49, (ItemStack) ItemManager.craftModerationGUIItem(Material.GLASS, "§fVanish"));
                tablistInventoryModeration.setItem(53, (ItemStack) ItemManager.craftModerationGUIItem(Material.BEDROCK, "§9Salle de Modération"));

                p.openInventory(tablistInventoryModeration);
                return true;
            }
        }
        return false;
    }
}
