package fr.romainmillan.moxed.commands;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.messages.SpawnMessages;
import fr.romainmillan.moxed.service.RankerService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandSpawn implements CommandExecutor {
    private Main main;
    public commandSpawn(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("spawn")){

            if(!(sender instanceof Player))
                return false;

            Player p = (Player) sender;

            String commandAtUse = "/spawn";

            if(args.length == 0){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "teleport " + p.getName() + " " + main.getConfig().getString("spawn"));
                p.sendMessage(SpawnMessages.TELEPORT_TO_SPAWN.getMessages());
                return true;
            }else if(args.length == 1){
                if(RankerService.isAdmin(p) || RankerService.isResponsable(p)){
                    commandAtUse = "/spawn [set]";
                    if(args[0].equalsIgnoreCase("set")){
                        main.getConfig().set("spawn", p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
                        main.saveConfig();
                        p.sendMessage(SpawnMessages.SET_SPAWN_SUCCESS.getMessages());
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
                p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                return false;
            }
        }

        return false;
    }
}
