package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class commandWarn implements CommandExecutor {
    private Main main;
    public commandWarn(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("warn")){
            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
                return false;
            }
            Player p = (Player) sender;
            String commandAtUse = "/warn [Player] <add/remove> <warn>";

            if(args.length==1){
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if(targetPlayer == null){
                    p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                    return false;
                }

                FileConfiguration warnplayer = YamlConfiguration.loadConfiguration(main.getFile("Warns"));
                if(warnplayer.contains(targetPlayer.getName())){

                }else {
                    p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Aucun warn à était recensée");
                }


            }else if(args.length > 3){

            }else {
                p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                return false;
            }
        }
        return false;
    }
}
