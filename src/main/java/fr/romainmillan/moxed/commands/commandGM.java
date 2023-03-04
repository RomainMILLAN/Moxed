package fr.romainmillan.moxed.commands;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.GMMessages;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.RankerService;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandGM implements CommandExecutor {
    private Main main;
    public commandGM(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player))
            return false;

        Player p = (Player) sender;

        if(label.equalsIgnoreCase("gm")){
            //Permission
            if(RankerService.isAdmin(p) || RankerService.isResponsable(p)){

                String commandAtUse = "/gm [gamemode/player] [gamemode]";

                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")){
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(GMMessages.GM_DEFINED_TO.getMessage() + "SURVIVAL");
                        return true;
                    }else if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")){
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(GMMessages.GM_DEFINED_TO.getMessage() + "CREATIVE");
                        return true;
                    }else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")){
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(GMMessages.GM_DEFINED_TO.getMessage() + "ADVENTURE");
                        return true;
                    }else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp")){
                        p.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(GMMessages.GM_DEFINED_TO.getMessage() + "SPECTATOR");
                        return true;
                    }else {
                        p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                        return false;
                    }
                }else if(args.length == 2){
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if(targetPlayer == null){
                        p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                        return false;
                    }

                    if(args[1].equalsIgnoreCase("0") || args[1].equalsIgnoreCase("s")){
                        targetPlayer.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §2" + targetPlayer.getDisplayName() + " §fa été défini sur §9SURVIVAL");
                        targetPlayer.sendMessage(GMMessages.GM_DEFINED_TO.getMessage() + "SURVIVAL");
                        return true;
                    }else if(args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("c")){
                        targetPlayer.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §2" + targetPlayer.getDisplayName() + " §fa été défini sur §9CREATIVE");
                        targetPlayer.sendMessage(GMMessages.GM_DEFINED_TO.getMessage() + "CREATIVE");
                        return true;
                    }else if(args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("a")){
                        targetPlayer.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §2" + targetPlayer.getDisplayName() + " §fa été défini sur §9ADVENTURE");
                        targetPlayer.sendMessage(GMMessages.GM_DEFINED_TO.getMessage() + "ADVENTURE");
                        return true;
                    }else if(args[1].equalsIgnoreCase("3") || args[1].equalsIgnoreCase("sp")){
                        targetPlayer.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §2" + targetPlayer.getDisplayName() + " §fa été défini sur §9SPECTATOR");
                        targetPlayer.sendMessage(GMMessages.GM_DEFINED_TO.getMessage() + "SPECTATOR");
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
