package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class commandStaff implements CommandExecutor {
    public Main main;
    public commandStaff(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("staff")){
            String commandAtUse = "/staff <on/off/set/see> [player] <on/off>";

            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.PREFIX_NOTAPLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;

            if(Main.administrateur.contains(p) || Main.responsables.contains(p) || Main.moderateur.contains(p)){
                if(args.length == 0 || args.length > 3){
                    p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("on")){
                        if(Main.Staff.contains(p)){
                            p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Votre mode Staff est déjà §9activé");
                            return false;
                        }else {
                            Main.Staff.add(p);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 250, true, false));
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode Staff est maintenant §9activé");
                            return true;
                        }
                    }else if(args[0].equalsIgnoreCase("off")){
                        if(Main.Staff.contains(p)){
                            Main.Staff.remove(p);
                            if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                            }
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Votre mode Staff est maintenant §9désactivé");
                            return true;
                        }else {
                            p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Votre mode Staff est déjà §9désactivé");
                            return false;
                        }
                    }else {
                        p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }else if(args.length == 2){
                    if(args[0].equalsIgnoreCase("see")){

                        Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                        if (targetPlayer == null) {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                            return false;
                        }

                        if(Main.Staff.contains(targetPlayer)){
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode Staff du joueur §6" + targetPlayer.getName() + " §fest §9activé");
                        }else {
                            p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode Staff du joueur §6" + targetPlayer.getName() + " §fest §9désactivé");
                        }
                        return true;
                    }else {
                        p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }else if(args.length == 3){
                    if(args[0].equalsIgnoreCase("set")){
                        Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                        if (targetPlayer == null) {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne");
                            return false;
                        }

                        if(args[2].equalsIgnoreCase("on")){
                            if(Main.Staff.contains(targetPlayer)){
                                p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Le mode Staff du joueur §6" + targetPlayer.getName() + " §fest déjà §9activé");
                                return false;
                            }else {
                                Main.Staff.add(p);
                                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode Staff du joueur §6" + targetPlayer.getName() + " §fest maintenant §9activé");
                                return true;
                            }
                        }else if(args[2].equalsIgnoreCase("off")){
                            if(Main.Staff.contains(targetPlayer)){
                                Main.Staff.remove(p);
                                p.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le mode Staff du joueur §6" + targetPlayer.getName() + " §fest maintenant §9désactivé");
                                return true;
                            }else {
                                p.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Le mode Staff du joueur §6" + targetPlayer.getName() + " §fest déjà §9désactivé");
                                return false;
                            }
                        }else {
                            p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                            return false;
                        }
                    }else {
                        p.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }
            }else {
                p.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;
    }
}
