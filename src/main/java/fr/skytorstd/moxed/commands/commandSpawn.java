package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandSpawn implements CommandExecutor {
    public Main main;
    public commandSpawn(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("spawn")){
            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commandAtUse = "/spawn <set>";

            if(args.length == 0){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + p.getName() + " " + main.getConfig().getString("spawnspace"));
                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu vient d'être téléporter au §9Spawn");
                return true;
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("set")){
                    if(Main.administrateur.contains(p) || p.isOp()){
                        main.getConfig().set("spawnspace", p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le §9Spawn §fvient d'être sauvegardé");
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
        }

        return false;
    }
}
