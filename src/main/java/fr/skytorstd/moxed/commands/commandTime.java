package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class commandTime implements CommandExecutor {
    public Main main;
    public commandTime(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("day")){
            if(sender.isOp() || Main.administrateur.contains(sender) || Main.responsables.contains(sender)){
                String commandAtUse = "/day";
                if(args.length == 0){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day");
                    sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de mettre le §9Jour");
                }else {
                    sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }
            }else {
                sender.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                return false;
            }
        }else if(label.equalsIgnoreCase("night")){
            if(sender.isOp() || Main.administrateur.contains(sender) || Main.responsables.contains(sender)){
                String commandAtUse = "/night";
                if(args.length == 0){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set night");
                    sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de mettre la §9Nuit");
                }else {
                    sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }
            }else {
                sender.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;
    }
}
