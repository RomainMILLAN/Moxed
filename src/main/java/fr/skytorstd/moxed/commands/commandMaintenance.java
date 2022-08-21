package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class commandMaintenance implements CommandExecutor {
    public Main main;
    public commandMaintenance(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("maintenance")){
            if(Main.administrateur.contains(sender) || sender.isOp()){
                String commandAtUse = "/maitenance <on/off/see>";

                if(args.length == 0 || args.length > 1){
                    sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }else {
                    if (args[0].equalsIgnoreCase("on")) {
                        if(Main.MAINTENANCE == false && main.getConfig().getString("maintenance").equalsIgnoreCase("off")){
                            Main.MAINTENANCE = true;
                            main.getConfig().set("maintenance", "on");

                            main.saveConfig();
                            sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "La maintenance vient d'être §9Activée");
                        }else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "La maintenance est déjà §9activée");
                            return false;
                        }
                    }else if(args[0].equalsIgnoreCase("off")){
                        if(Main.MAINTENANCE == true && main.getConfig().getString("maintenance").equalsIgnoreCase("on")){
                            Main.MAINTENANCE = false;
                            main.getConfig().set("maintenance", "off");

                            main.saveConfig();
                            sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "La maintenance vient d'être §9Désactivée");
                        }else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "La maintenance est déjà §9désactivée");
                            return false;
                        }
                    }else if(args[0].equalsIgnoreCase("see")){
                        sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "La maintenance à le status §9" + main.getConfig().getString("maintenance"));
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
