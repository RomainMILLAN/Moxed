package fr.romainmillan.moxed.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
    public static Object craftItem(Material material, String name){
        ItemStack itemstack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemstack.setItemMeta(itemMeta);
        return itemstack;
    }

    public static Object craftItemNone(){
        ItemStack itemstack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemstack.setItemMeta(itemMeta);
        return itemstack;
    }
}
