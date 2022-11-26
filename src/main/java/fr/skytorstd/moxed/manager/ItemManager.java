package fr.skytorstd.moxed.manager;

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
}
