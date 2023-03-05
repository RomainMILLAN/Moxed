package fr.romainmillan.moxed.commands;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.messages.StaffMessages;
import fr.romainmillan.moxed.service.RankerService;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class commandStaff implements CommandExecutor {
    private Main main;
    public commandStaff(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("staff")){

            if(!(sender instanceof Player)){
                sender.sendMessage(MoxedMessage.EM_NOT_PLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commandAtUse = "/staff";

            if(RankerService.isStaff(p)){
                if(args.length == 0){
                    if(Main.staffPlayer.contains(p)){
                        Main.staffPlayer.remove(p);
                        p.sendMessage(StaffMessages.STAFF_DESACTIVATE.getMessage());

                        if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                        p.setGameMode(GameMode.SURVIVAL);

                        return true;
                    }else {
                        Main.staffPlayer.add(p);
                        p.sendMessage(StaffMessages.STAFF_ACTIVATE.getMessage());

                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 250, true, false));
                        p.setGameMode(GameMode.SPECTATOR);

                        return true;
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
