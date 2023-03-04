package fr.romainmillan.moxed.commands;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.messages.TimeMessages;
import fr.romainmillan.moxed.service.RankerService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandTime implements CommandExecutor {
    private Main main;
    public commandTime(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("day")){

            String commandAtUse = "/day";
            boolean playerHasPermission = false;

            if(sender instanceof Player){
                Player p = (Player) sender;

                if(RankerService.isAdmin(p) || RankerService.isResponsable(p)){
                    playerHasPermission = true;
                }else {
                    p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                }
            }else {
                playerHasPermission = true;
            }

            if(playerHasPermission){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day");
                sender.sendMessage(TimeMessages.SWITCH_TIME_MESSAGE.getMessages() + "JOUR");
                return true;
            }else {
                return false;
            }

        }else if(label.equalsIgnoreCase("night")){

            String commandAtUse = "/night";
            boolean playerHasPermission = false;

            if(sender instanceof Player){
                Player p = (Player) sender;

                if(RankerService.isAdmin(p) || RankerService.isResponsable(p)){
                    playerHasPermission = true;
                }else {
                    p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                }
            }else {
                playerHasPermission = true;
            }

            if(playerHasPermission){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set night");
                sender.sendMessage(TimeMessages.SWITCH_TIME_MESSAGE.getMessages() + "NUIT");
                return true;
            }else {
                return false;
            }

        }

        return false;
    }
}
