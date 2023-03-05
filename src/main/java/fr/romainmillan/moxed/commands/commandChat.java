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

public class commandChat implements CommandExecutor {
    private Main main;
    public commandChat(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if(label.equalsIgnoreCase("cc")){

            if(!(sender instanceof Player)){
                sender.sendMessage(MoxedMessage.EM_NOT_PLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commandAtUse = "/cc <clear/mute> [player]";

            if(RankerService.isStaff(p)){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("clear")){
                        for (int i = 0; i < 100; i++) {
                            Bukkit.broadcastMessage(" ");
                        }
                        p.sendMessage(ModerationMessages.CLEAR_SUCCESS.getMessages());
                        return true;
                    }else {
                        p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                        return false;
                    }
                }else if(args.length == 2){
                    if(args[0].equalsIgnoreCase("mute")){
                        Player targetPlayer = (Player) Bukkit.getServer().getPlayer(args[1]);
                        if(targetPlayer == null){
                            p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                            return false;
                        }

                        if(Main.mutePlayer.contains(targetPlayer)){
                            Main.mutePlayer.remove(targetPlayer);
                            p.sendMessage(ModerationMessages.UNMUTE_PERSON.getMessages() + RankerService.getStringPlayerRank(targetPlayer, main));
                            targetPlayer.sendMessage(ModerationMessages.PEOPLE_UNMUTE.getMessages() + RankerService.getStringPlayerRank(p, main));
                        }else {
                            Main.mutePlayer.add(targetPlayer);
                            p.sendMessage(ModerationMessages.MUTE_PERSON.getMessages() + RankerService.getStringPlayerRank(targetPlayer, main));
                            targetPlayer.sendMessage(ModerationMessages.PEOPLE_MUTE.getMessages() + RankerService.getStringPlayerRank(p, main));
                        }

                        return true;
                    }else {
                        p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                        return false;
                    }
                }else {
                    p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                    return false;
                }
            }else {
                p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;

    }
    

}
