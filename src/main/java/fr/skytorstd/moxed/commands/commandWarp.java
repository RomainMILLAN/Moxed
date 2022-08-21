package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Set;

public class commandWarp implements CommandExecutor {
    public Main main;
    public commandWarp(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("warp")){
            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commandAtUse = "/warp <set/list/[Nom_du_warp]> [Nom_du_warp]";

            if(args.length == 0 || args.length > 2){
                p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                return false;
            }else {
                if(args[0].equalsIgnoreCase("set") && args.length == 2){
                    if(Main.administrateur.contains(p) || Main.responsables.contains(p) || p.isOp()){
                        FileConfiguration warpFile = YamlConfiguration.loadConfiguration(main.getFile("ModiWarp"));
                        warpFile.set("Warps." + args[1], p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le warp §9" + args[1] + " §fviens d'être sauvegardé");
                        try {
                            warpFile.save(main.getFile("ModiWarp"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        p.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                        return false;
                    }
                }else if(args[0].equalsIgnoreCase("list") && args.length == 1){
                    FileConfiguration warpFile = YamlConfiguration.loadConfiguration(main.getFile("ModiWarp"));
                    Set<String> warps = warpFile.getConfigurationSection("Warps").getKeys(false);
                    String warpsListString = "";
                    for(String s : warps){
                        warpsListString += s + "§f,§9";
                    }
                    p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Liste des warps: §9" + warpsListString);
                }else {
                    FileConfiguration warpFile = YamlConfiguration.loadConfiguration(main.getFile("ModiWarp"));
                    if(warpFile.contains("Warps." + args[0])){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + p.getName() + " " + warpFile.getString("Warps." + args[0]));
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Téléportation vers §9" + args[0]);
                    }else {
                        p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Ce warp n'existe pas");
                        return false;
                    }
                }
            }
        }


        return false;
    }
}
