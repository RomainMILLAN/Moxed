package fr.romainmillan.moxed.commands;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.ModerationService;
import fr.romainmillan.moxed.service.RankerService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandFreeze implements CommandExecutor {
    private Main main;
    public commandFreeze(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("freeze")){

            if(!(sender instanceof Player)){
                sender.sendMessage(MoxedMessage.EM_NOT_PLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commandAtUse = "/freeze [player]";

            if(RankerService.isInStaff(p)){

                if(args.length == 1){
                    Player targetPlayer = (Player) Bukkit.getServer().getPlayer(args[0]);

                    if(targetPlayer == null){
                        p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                        return false;
                    }

                    ModerationService.freezeTargetPlayer(p, targetPlayer, main);

                }else {
                    p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                    return false;
                }

            }else{
                p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;
    }
}
