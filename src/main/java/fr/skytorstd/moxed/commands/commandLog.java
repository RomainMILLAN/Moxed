package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class commandLog implements CommandExecutor {
    public Main main;
    public commandLog(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("logs")){
            if(Main.administrateur.contains(sender) || Main.responsables.contains(sender) || sender.isOp()){
                String commandAtUse = "/logs <on/off/[others]> <on/off>";
                if(args.length == 0){
                    sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("on")){
                        if(main.getConfig().contains("Logs.status")){
                            if(main.getConfig().getString("Logs.status").equalsIgnoreCase("off")){
                                main.getConfig().set("Logs.status", "on");
                                main.saveConfig();
                                sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Les logs viennent d'être §9Activé");
                                return true;
                            }else {
                                sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Les logs sont déjà activé");
                                return false;
                            }
                        }else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Une erreur est survenue, merci de réessayer");
                            return false;
                        }
                    }else if(args[0].equalsIgnoreCase("off")){
                        if(main.getConfig().contains("Logs.status")){
                            if(main.getConfig().getString("Logs.status").equalsIgnoreCase("on")){
                                main.getConfig().set("Logs.status", "off");
                                main.saveConfig();
                                sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Les logs viennent d'être §9Désactivé");
                                return true;
                            }else {
                                sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Les logs sont déjà désactivé");
                                return false;
                            }
                        }else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Une erreur est survenue, merci de réessayer");
                            return false;
                        }
                    }else {
                        sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }else if(args.length == 2){
                    if(args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("off")){
                        if(main.getConfig().contains("Logs." + args[0])){
                            if(!main.getConfig().getString("Logs." + args[0]).equalsIgnoreCase(args[1])){
                                main.getConfig().set("Logs." + args[0], args[1]);
                                main.saveConfig();
                                if(args[1].equalsIgnoreCase("on")){
                                    sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Les logs de " + args[0] + " viennent d'être §9Activé");
                                    return false;
                                }else {
                                    sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Les logs de " + args[0] + " viennent d'être §9Désactivé");
                                    return false;
                                }
                            }else {
                                if(args[1].equalsIgnoreCase("on")){
                                    sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Les logs de " + args[0] + " sont déjà activé");
                                    return false;
                                }else {
                                    sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Les logs de " + args[0] + " sont déjà désactivé");
                                    return false;
                                }
                            }
                        }else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Une erreur est survenue, merci de réessayer");
                            return false;
                        }
                    }else {
                        sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
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
