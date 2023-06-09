package fr.romainmillan.moxed.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.ModerationMessages;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.RankerService;

public class commandMaintenance implements CommandExecutor {
    private Main main;

    public commandMaintenance(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("maintenance")){

            String commandAtUse = "/maintenance <on/off>";
            if(args.length == 1 && (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off"))){
                if(!(sender instanceof Player)){
                    return maintenance(args[0], sender, commandAtUse);
                }else {
                    Player p = (Player) sender;
        
                    if(RankerService.isAdmin(p) && RankerService.isInStaff(p)){
                        return maintenance(args[0], sender, commandAtUse);
                    }else {
                        p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                        return false;
                    }
                }
            }else {
                sender.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                return false;
            }
        }

        return false;
    }

    /**
     * Permet la gestion de la maintenance
     * <pre/>
     * 
     * @param state
     * @param sender
     * @return
     */
    private boolean maintenance(String state, CommandSender sender, String commandAtUse){
        if(state.equalsIgnoreCase("on")){
            if(main.getConfig().get("maintenance").equals("off")){
                main.getConfig().set("maintenance", "on");
                main.Maintenance = true;
                sender.sendMessage(ModerationMessages.MAINTENANCE_ON.getMessages());

                for(Player player : Bukkit.getOnlinePlayers()){
                    if(RankerService.isAdmin(player) || RankerService.isResponsable(player) || RankerService.isModerator(player)){
                        player.sendMessage(MoxedMessage.PREFIX_STAFF.getMessage() + "Le serveur vient de passer en §6Maintenance");
                    }else {
                        player.kickPlayer(ModerationMessages.MAINTENANCE_ACTIVATE.getMessages());
                    }
                }

                return true;
            }else {
                sender.sendMessage(ModerationMessages.MAINTENANCE_ON_IMPOSSIBLE.getMessages());
                return false;
            }
        }else if(state.equals("off")){
            if(main.getConfig().get("maintenance").equals("on")){
                main.getConfig().set("maintenance", "off");
                main.Maintenance = false;
                sender.sendMessage(ModerationMessages.MAINTENANCE_OFF.getMessages());
                return true;
            }else {
                sender.sendMessage(ModerationMessages.MAINTENANCE_OFF_IMPOSSIBLE.getMessages());
                return false;
            }
        }else {
            sender.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
            return false;
        }
    }

}
