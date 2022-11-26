package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.ItemManager;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class commandTicket implements CommandExecutor {
    public Main main;
    public commandTicket(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandAtUse = "/ticket <message>";

        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
            return false;
        }
        Player p = (Player) sender;

        if(Main.Staff.contains(p)){
            commandAtUse = "/ticket";

            Inventory ticketInventory = Bukkit.createInventory(null, getScaleInventoryTicket(Main.Tickets.size()), "Liste des Tickets");

            int i=0;
            for(Map.Entry<Player, String> map : Main.Tickets.entrySet()){
                ticketInventory.setItem(i, (ItemStack) ItemManager.craftItem(Material.PAPER, map.getKey().getName() + " -> " + map.getValue()));
                i++;
            }

            p.openInventory(ticketInventory);
            return true;
        }else {
            if(args.length > 1){
                if(Main.Tickets.size() < 54){
                    //TICKET NAME
                    String ticketName = "";
                    for(int i=0; i<args.length; i++){
                        ticketName = ticketName + args[i] + " ";
                    }
                    ticketName = ticketName.substring(0, ticketName.length()-1);

                    //CREATE TICKET
                    Main.Tickets.put(p, ticketName);

                    //RETURN
                    p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de créer un §9ticket §fpour '"+ticketName+"'");

                    for(Player player : Bukkit.getOnlinePlayers()){
                        if(Main.Staff.contains(player)){
                            player.sendMessage(Messages.PREFIX_STAFF.getMessage() + "Nouveau ticket de " + p.getName());
                        }
                    }

                    return true;
                }else {
                    p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Il y a déjà trop de ticket en attente, merci de réessayer plus tard");
                    return false;
                }
            }else {
                p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                return false;
            }
        }
    }

    private static int getScaleInventoryTicket(int nbTicket){
        if(nbTicket <= 9){
            return 1*9;
        }else if(nbTicket <= 18){
            return 2*9;
        }else if(nbTicket <= 27){
            return 3*9;
        }else if(nbTicket <= 36){
            return 4*9;
        }else if(nbTicket <= 45){
            return 5*9;
        }else {
            return 6*9;
        }
    }
}
