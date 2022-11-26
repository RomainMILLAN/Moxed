package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.ItemManager;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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


            //SET PLACE OF MODERATION
            if(args.length == 1 && args[0].equalsIgnoreCase("set")){
                if(Main.administrateur.contains(p) || Main.responsables.contains(p) || Main.moderateur.contains(p) || p.isOp()){
                    String commandAtUse = "/moderation <set>";
                    if(args.length == 0){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + p.getName() + " " + main.getConfig().getString("modspace"));
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Téléportation vers l'§9Espace de Modération");
                        return true;
                    }else if(args.length == 1){
                        if(args[0].equalsIgnoreCase("set")){
                            if(Main.administrateur.contains(p) || p.isOp()){
                                main.getConfig().set("modspace", p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
                                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "l'§9Espace de Modération §fviens d'être sauvegardé");
                                return true;
                            }else {
                                p.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                                return false;
                            }
                        }else {
                            p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                            return false;
                        }
                    }else {
                        p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }else {
                    p.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                    return false;
                }

            }



            //GUI OF MODERATION
            String commandAtUse = "/moderation <player>";
            if(args.length != 1){
                p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                return false;
            }else {
                if(Main.Staff.contains(p)){
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                        return false;
                    }

                    //INV MODERATION
                    int scale = 6*9;
                    Inventory tablistInventoryModeration = Bukkit.createInventory(null, scale, targetPlayer.getName());

                    tablistInventoryModeration.setItem(0, (ItemStack) ItemManager.craftItem(Material.PAPER, "§fDirect Message"));
                    tablistInventoryModeration.setItem(10, (ItemStack) ItemManager.craftItem(Material.NETHERITE_AXE, "§cBan"));
                    tablistInventoryModeration.setItem(13, (ItemStack) ItemManager.craftItem(Material.PAPER, "§9Mute"));
                    tablistInventoryModeration.setItem(16, (ItemStack) ItemManager.craftItem(Material.ANVIL, "§6Kick"));
                    tablistInventoryModeration.setItem(20, (ItemStack) ItemManager.craftItem(Material.COBBLESTONE, "§8GM 0"));
                    tablistInventoryModeration.setItem(21, (ItemStack) ItemManager.craftItem(Material.GRASS_BLOCK, "§2GM 1"));
                    tablistInventoryModeration.setItem(23, (ItemStack) ItemManager.craftItem(Material.SPRUCE_LOG, "§cGM 2"));
                    tablistInventoryModeration.setItem(24, (ItemStack) ItemManager.craftItem(Material.BARRIER, "§fGM 3"));
                    tablistInventoryModeration.setItem(30, (ItemStack) ItemManager.craftItem(Material.ENDER_PEARL, "§fGoto"));
                    tablistInventoryModeration.setItem(32, (ItemStack) ItemManager.craftItem(Material.ENDER_EYE, "§fBring"));
                    tablistInventoryModeration.setItem(49, (ItemStack) ItemManager.craftItem(Material.GLASS, "§fVanish"));
                    tablistInventoryModeration.setItem(53, (ItemStack) ItemManager.craftItem(Material.BEDROCK, "§9Salle de Modération"));

                    p.openInventory(tablistInventoryModeration);
                    return true;
                }else {
                    p.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                    return false;
                }
            }
        }
        return false;
    }
}
