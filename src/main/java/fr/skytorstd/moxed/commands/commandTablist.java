package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class commandTablist implements CommandExecutor {
    public Main main;
    public commandTablist(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
        }
        Player p = (Player) sender;

        if(label.equalsIgnoreCase("tablist")){
            if(Main.administrateur.contains(p) || Main.responsables.contains(p) || Main.moderateur.contains(p) || p.isOp()){
                if(args.length == 0){
                    int scale = 5*9;
                    Inventory tablistInventory = Bukkit.createInventory(null, scale, "Tablist");
                    int i = 0;
                    for(Player playersOnlines : Bukkit.getOnlinePlayers()){
                        ItemStack player = new ItemStack(Material.PLAYER_HEAD, 1);
                        ItemMeta playerMeta = player.getItemMeta();
                        playerMeta.setDisplayName(playersOnlines.getName());
                        player.setItemMeta(playerMeta);
                        tablistInventory.setItem(i, player);
                        System.out.println(i);
                        i = i + 1;
                        System.out.println(i);
                    }

                    p.openInventory(tablistInventory);
                    return true;
                }else if(args.length == 1){
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne sur le serveur !");
                        return false;
                    }

                    if(Main.Staff.contains(p)){
                        //INV MODERATION
                        int scale = 6*9;
                        Inventory tablistInventoryModeration = Bukkit.createInventory(null, scale, targetPlayer.getName());

                        tablistInventoryModeration.setItem(0, (ItemStack) this.createItemStackStaff(Material.PAPER, "§fDirect Message"));
                        tablistInventoryModeration.setItem(10, (ItemStack) this.createItemStackStaff(Material.NETHERITE_AXE, "§cBan"));
                        tablistInventoryModeration.setItem(13, (ItemStack) this.createItemStackStaff(Material.PAPER, "§9Mute"));
                        tablistInventoryModeration.setItem(16, (ItemStack) this.createItemStackStaff(Material.ANVIL, "§6Kick"));
                        tablistInventoryModeration.setItem(20, (ItemStack) this.createItemStackStaff(Material.COBBLESTONE, "§8GM 0"));
                        tablistInventoryModeration.setItem(21, (ItemStack) this.createItemStackStaff(Material.GRASS_BLOCK, "§2GM 1"));
                        tablistInventoryModeration.setItem(23, (ItemStack) this.createItemStackStaff(Material.SPRUCE_LOG, "§cGM 2"));
                        tablistInventoryModeration.setItem(24, (ItemStack) this.createItemStackStaff(Material.BARRIER, "§fGM 3"));
                        tablistInventoryModeration.setItem(30, (ItemStack) this.createItemStackStaff(Material.ENDER_PEARL, "§fGoto"));
                        tablistInventoryModeration.setItem(32, (ItemStack) this.createItemStackStaff(Material.ENDER_EYE, "§fBring"));
                        tablistInventoryModeration.setItem(49, (ItemStack) this.createItemStackStaff(Material.GLASS, "§fVanish"));
                        tablistInventoryModeration.setItem(53, (ItemStack) this.createItemStackStaff(Material.BEDROCK, "§9Salle de Modération"));

                        p.openInventory(tablistInventoryModeration);
                        return true;
                    }else {
                        int scale = 1*9;
                        Inventory tablistInventory = Bukkit.createInventory(null, scale, targetPlayer.getName());

                        tablistInventory.setItem(0, (ItemStack) this.createItemStackStaff(Material.PAPER, "§fDirect Message"));

                        p.openInventory(tablistInventory);
                        return true;
                    }

                }else {
                    p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + "/tablist [player]");
                    return false;
                }
            }else {
                p.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;
    }


    public Object createItemStackStaff(Material material, String name){
        ItemStack itemstack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemstack.setItemMeta(itemMeta);
        return itemstack;
    }
}
