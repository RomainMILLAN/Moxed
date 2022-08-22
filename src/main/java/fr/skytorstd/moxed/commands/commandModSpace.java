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

public class commandModSpace implements CommandExecutor {
    public Main main;
    public commandModSpace(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("modspace")){
            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;

            if(Main.administrateur.contains(p) || Main.responsables.contains(p) || Main.moderateur.contains(p) || p.isOp()){
                String commandAtUse = "/modspace <set>";
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

        return false;
    }
}
