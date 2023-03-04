package fr.romainmillan.moxed.commands;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.RankerService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandDM implements CommandExecutor {
    private Main main;
    public commandDM(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("dm") || label.equalsIgnoreCase("mp")){
            if(!(sender instanceof Player)){
                sender.sendMessage(MoxedMessage.EM_NOT_PLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commandAtUse = "/dm [player] message...";

            if(args.length < 2){
                p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                return false;
            }else {
                Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                if(targetPlayer == null){
                    sender.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                    return false;
                }

                String dm = "";
                for(int i=1; i<args.length; i++){
                    dm += args[i] + " ";
                }

                p.sendMessage("§d[DM] " + RankerService.getStringPlayerRank(targetPlayer, main) + " §d-▶ " + dm);
                targetPlayer.sendMessage("§d[DM] " + RankerService.getStringPlayerRank(p, main) + " §d◀- " + dm);

                /*for(Player playersOnline : Bukkit.getOnlinePlayers()){
                    if(Main.Staff.contains(playersOnline)){
                        playersOnline.sendMessage("§d[" + p.getName() + "] -▶ [" + targetPlayer.getName() + "]: " + dm);
                    }
                }*/

                return true;
            }
        }

        return false;
    }
}
