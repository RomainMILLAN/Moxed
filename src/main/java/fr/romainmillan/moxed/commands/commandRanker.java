package fr.romainmillan.moxed.commands;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.MoxedPermissions;
import fr.romainmillan.moxed.manager.ItemManager;
import fr.romainmillan.moxed.manager.Messages;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.RankerService;
import fr.romainmillan.moxed.state.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class commandRanker implements CommandExecutor {
    public Main main;

    public commandRanker(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("ranker")){

            if(!(sender instanceof Player))
                return false;

            Player p = (Player) sender;

            //Permission
            if(p.isOp()){

                String commandAtUse = "/ranker [player]";

                if(args.length == 1){
                    Player targetPlayer = (Player) Bukkit.getServer().getPlayer(args[0]);

                    if(targetPlayer == null){
                        p.sendMessage(MoxedMessage.EM_PLAYER_NOT_CONNECTED.getMessage());
                        return false;
                    }

                    Inventory rankerInventory = Bukkit.createInventory(null, 1*9, "Ranker > " + targetPlayer.getName());

                    String rankPlayer = RankerService.getRanksPlayerWithColor(p);
                    rankerInventory.setItem(0, (ItemStack) ItemManager.craftItem(Material.PAPER, rankPlayer));

                    //SET
                    rankerInventory.setItem(8, (ItemStack) ItemManager.craftItem(Material.BARRIER, RankerService.getRanksWithRanksState(Ranks.ADMINISTRATEUR)));
                    rankerInventory.setItem(7, (ItemStack) ItemManager.craftItem(Material.ANVIL, RankerService.getRanksWithRanksState(Ranks.RESPONSABLE)));
                    rankerInventory.setItem(6, (ItemStack) ItemManager.craftItem(Material.PAPER, RankerService.getRanksWithRanksState(Ranks.MODERATEUR)));
                    rankerInventory.setItem(5, (ItemStack) ItemManager.craftItem(Material.GRASS_BLOCK, RankerService.getRanksWithRanksState(Ranks.JOUEUR)));

                    p.openInventory(rankerInventory);
                    return true;
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
