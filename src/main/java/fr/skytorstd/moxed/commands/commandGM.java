package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandGM implements CommandExecutor {
    public Main main;
    public commandGM(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
        }

        Player p = (Player) sender;

        if(label.equalsIgnoreCase("gm")){
            if(p.isOp() || Main.administrateur.contains(p) || Main.responsables.contains(p) || Main.moderateur.contains(p)){
                String commandAtUse = "/gm <gamemode/player]> [gamemode]";
                if(args.length == 0){
                    p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")){
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9SURVIVAL");
                        return true;
                    }else if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")){
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + " Votre mode de jeu a été défini sur §9CREATIVE");
                        return true;
                    }else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")){
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9ADVENTURE");
                        return true;
                    }else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp")){
                        p.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9SPECTATOR");
                        return true;
                    }else {
                        p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }else if(args.length == 2){
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if(targetPlayer == null){
                        p.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                        return false;
                    }
                    Player target = targetPlayer;

                    if(args[1].equalsIgnoreCase("0") || args[1].equalsIgnoreCase("s")){
                        target.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §2" + target.getDisplayName() + " §fa été défini sur §9SURVIVAL");
                        target.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9SURVIVAL");
                        return true;
                    }else if(args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("c")){
                        target.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §2" + target.getDisplayName() + " §fa été défini sur §9CREATIVE");
                        target.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9CREATIVE");
                        return true;
                    }else if(args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("a")){
                        target.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §2" + target.getDisplayName() + " §fa été défini sur §9ADVENTURE");
                        target.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9ADVENTURE");
                        return true;
                    }else if(args[1].equalsIgnoreCase("3") || args[1].equalsIgnoreCase("sp")){
                        target.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode de jeu de §2" + target.getDisplayName() + " §fa été défini sur §9SPECTATOR");
                        target.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode de jeu a été défini sur §9SPECTATOR");
                        return true;
                    }else {
                        p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }else {
                    p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }
            }else {
                p.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;
    }
}
