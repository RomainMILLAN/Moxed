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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class commandWarn implements CommandExecutor {
    private Main main;
    public commandWarn(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("warn")){
            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
                return false;
            }
            Player p = (Player) sender;

            if(Main.Staff.contains(p)){
                String commandAtUse = "/warn [Player] <add/remove> [id/warn]";

                FileConfiguration warnsFile = YamlConfiguration.loadConfiguration(main.getFile("Warns"));

                if(args.length == 1 && !args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove")){
                    //Warn see of player
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                        return false;
                    }

                    if(warnsFile.contains(targetPlayer.getName())){
                        //GET les warns
                        List<String> warnsPlayer = warnsFile.getStringList(targetPlayer.getName());
                        HashMap<Integer, String> warnsPlayerWithId = new HashMap<>();
                        for(int i=1; i<warnsPlayer.size(); i++){
                            warnsPlayerWithId.put(i, warnsPlayer.get(i));
                        }
                        //Affichages des warns
                        Inventory warnsInventoryPlayer = Bukkit.createInventory(null, getScaleInventoryWarn(warnsPlayer.size()), targetPlayer.getName() + " > Warns");

                        int i=0;
                        for(Map.Entry<Integer, String> map : warnsPlayerWithId.entrySet()){
                            warnsInventoryPlayer.setItem(i, (ItemStack) ItemManager.craftItem(Material.ANVIL, map.getKey() + " | " + map.getValue()));
                            i++;
                        }

                        p.openInventory(warnsInventoryPlayer);

                        return true;
                    }else {
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "ce joueur n'as aucun §9warn");
                        return true;
                    }
                }else if(args.length >= 3) {
                    //Warn see of player
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                        return false;
                    }

                    if(args[1].equalsIgnoreCase("remove")){
                        //GET les warns
                        List<String> warnsPlayer = warnsFile.getStringList(targetPlayer.getName());
                        HashMap<Integer, String> warnsPlayerWithId = new HashMap<>();
                        for(int i=1; i<warnsPlayer.size(); i++){
                            warnsPlayerWithId.put(i, warnsPlayer.get(i));
                        }

                        Integer id = Integer.valueOf(args[2])-1;
                        String warnName = warnsPlayer.get(id);
                        for(Map.Entry<Integer, String> map : warnsPlayerWithId.entrySet()){
                            if(map.getKey().equals(id)){
                                warnsPlayerWithId.remove(map.getKey());
                                break;
                            }
                        }

                        warnsPlayer.clear();
                        for(Map.Entry<Integer, String> map : warnsPlayerWithId.entrySet()){
                            warnsPlayer.add(map.getValue());
                        }

                        warnsFile.set(targetPlayer.getName(), warnsPlayer);

                        try {
                            warnsFile.save(main.getFile("Warns"));
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Warn '"+warnName+"' §9supprimé");
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(args[1].equalsIgnoreCase("add")){
                        //GET les warns
                        List<String> warnsPlayer = warnsFile.getStringList(targetPlayer.getName());
                        String warnName = "";
                        for(int i=2; i<args.length; i++){
                            warnName += args[i] + " ";
                        }
                        warnName = warnName.substring(0, warnName.length()-1);

                        warnsPlayer.add(warnName);

                        warnsFile.set(targetPlayer.getName(), warnsPlayer);

                        try {
                            warnsFile.save(main.getFile("Warns"));
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Warn '"+warnName+"' §9enregistré");
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
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
        return false;
    }

    private static int getScaleInventoryWarn(int nbWarn){
        if(nbWarn <= 9){
            return 1*9;
        }else if(nbWarn <= 18){
            return 2*9;
        }else if(nbWarn <= 27){
            return 3*9;
        }else if(nbWarn <= 36){
            return 4*9;
        }else if(nbWarn <= 45){
            return 5*9;
        }else {
            return 6*9;
        }
    }
}
