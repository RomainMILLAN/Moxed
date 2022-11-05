package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class commandFreeze implements CommandExecutor {
    public Main main;
    public commandFreeze(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("freeze")){
            if(Main.administrateur.contains(sender) || Main.responsables.contains(sender) || Main.moderateur.contains(sender) || sender.isOp()){
                String commandAtUse = "/freeze [player] <on/off/see>";

                if(args.length != 2){
                    sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }else {
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if (targetPlayer == null) {
                        sender.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                        return false;
                    }

                    if(args[1].equalsIgnoreCase("on")){
                        if(!Main.Freeze.contains(targetPlayer)){
                            Main.Freeze.add(targetPlayer);
                            sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §fvient de se faire §9freeze");
                            targetPlayer.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de te faire §9freeze §fpar §6" + sender.getName());
                            return true;
                        }else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §cest déjà freeze");
                            return false;
                        }
                    }else if(args[1].equalsIgnoreCase("off")){
                        if(Main.Freeze.contains(targetPlayer)){
                            Main.Freeze.remove(targetPlayer);
                            sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §fvient de se faire §9unfreeze");
                            targetPlayer.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Tu viens de te faire §9unfreeze §fpar §6" + sender.getName());
                            return true;
                        }else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §cn'est pas freeze");
                            return false;
                        }
                    }else if(args[1].equalsIgnoreCase("see")){
                        if(!Main.Freeze.contains(targetPlayer)){
                            sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §fest §9freeze");
                            return true;
                        }else {
                            sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getName() + " §fn'est pas §9freeze");

                            return true;
                        }
                    }else {
                        sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }
            }else {
                sender.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;
    }
}
