package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandDM implements CommandExecutor {
    public Main main;
    public commandDM(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("dm") || label.equalsIgnoreCase("mp")){
            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commandAtUse = "/<dm/mp> [player] message...";

            if(args.length < 2){
                p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                return false;
            }else {
                Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                if(targetPlayer == null || targetPlayer==p){
                    sender.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                    return false;
                }

                String dm = "";
                for(int i=1; i<args.length; i++){
                    dm += args[i] + " ";
                }

                targetPlayer.sendMessage("§d◀-[" + p.getName() + "]: " + dm);
                p.sendMessage("§d-▶[" + p.getName() + "]: " + dm);

                for(Player playersOnline : Bukkit.getOnlinePlayers()){
                    if(Main.Staff.contains(playersOnline)){
                        playersOnline.sendMessage("§d[" + p.getName() + "] -▶ [" + targetPlayer.getName() + "]: " + dm);
                    }
                }
            }
        }

        return false;
    }
}
