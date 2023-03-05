package fr.romainmillan.moxed.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.manager.ItemManager;
import fr.romainmillan.moxed.messages.ModerationMessages;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.service.RankerService;

public class commandWarn implements CommandExecutor {
    private Main main;

    public commandWarn(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("warns")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MoxedMessage.EM_NOT_PLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commantAtUse = "/warns [player] <add/remove> [id/warn]";

            if (RankerService.isStaff(p)) {

                FileConfiguration warnsFile = YamlConfiguration.loadConfiguration(main.getFile("warns"));
                Player targetPlayer = (Player) Bukkit.getServer().getPlayer(args[0]);
                if (targetPlayer == null) {
                    p.sendMessage(MoxedMessage.EM_NOT_PLAYER.getMessage());
                    return false;
                }

                if (args.length == 1) {

                    if (warnsFile.contains(targetPlayer.getName())) {

                        List<String> warnsListPlayer = warnsFile.getStringList(targetPlayer.getName());

                        if (warnsListPlayer.size() > 0) {
                            HashMap<Integer, String> warnsPlayerWithId = new HashMap<>();
                            int i = 1;
                            for (String str : warnsListPlayer) {
                                warnsPlayerWithId.put(i, str);
                                i++;
                            }

                            Inventory warnsInventoryPlayer = Bukkit.createInventory(null,
                                    getScaleInventoryWarn(warnsListPlayer.size()), targetPlayer.getName() + " > Warns");

                            i = 0;
                            for (Map.Entry<Integer, String> map : warnsPlayerWithId.entrySet()) {
                                warnsInventoryPlayer.setItem(i, (ItemStack) ItemManager.craftItem(Material.ANVIL,
                                        map.getKey() + " | " + map.getValue()));
                                i++;
                            }

                            p.openInventory(warnsInventoryPlayer);

                            return true;
                        } else {
                            p.sendMessage(ModerationMessages.NO_WARNS.getMessages());
                            return true;
                        }

                    } else {
                        p.sendMessage(ModerationMessages.NO_WARNS.getMessages());
                        return true;
                    }

                } else if (args.length > 2) {
                    if (args[1].equalsIgnoreCase("add")) {

                        List<String> warnsPlayer = warnsFile.getStringList(targetPlayer.getName());
                        String warnName = "";
                        for (int i = 2; i < args.length; i++) {
                            warnName += args[i] + " ";
                        }
                        warnName = warnName.substring(0, warnName.length() - 1);

                        warnsPlayer.add(warnName);

                        warnsFile.set(targetPlayer.getName(), warnsPlayer);

                        try {
                            warnsFile.save(main.getFile("warns"));
                            p.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Warn '" + warnName + "' §9enregistré");
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (args[1].equalsIgnoreCase("remove")) {

                        List<String> warnsPlayer = warnsFile.getStringList(targetPlayer.getName());
                        HashMap<Integer, String> warnsPlayerWithId = new HashMap<>();
                        int i = 1;
                        for (String str : warnsPlayer) {
                            warnsPlayerWithId.put(i, str);
                            i++;
                        }

                        Integer id = Integer.valueOf(args[2]) - 1;
                        String warnName = warnsPlayer.get(id);
                        for (Map.Entry<Integer, String> map : warnsPlayerWithId.entrySet()) {
                            if (map.getKey().equals(id + 1)) {
                                warnsPlayerWithId.remove(map.getKey());
                                break;
                            }
                        }

                        warnsPlayer.clear();
                        for (Map.Entry<Integer, String> map : warnsPlayerWithId.entrySet()) {
                            warnsPlayer.add(map.getValue());
                        }

                        warnsFile.set(targetPlayer.getName(), warnsPlayer);

                        try {
                            warnsFile.save(main.getFile("warns"));
                            p.sendMessage(MoxedMessage.PREFIX_NORMAL.getMessage() + "Warn '" + warnName + "' §9supprimé");
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commantAtUse);
                        return false;
                    }
                } else {
                    p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commantAtUse);
                    return false;
                }
            } else {
                p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;

    }

    /**
     * Permet de connaitre la taille de l'inventaire des warns
     * 
     * <pre/>
     * 
     * @param nbWarn
     * @return
     */
    private static int getScaleInventoryWarn(int nbWarn) {
        if (nbWarn <= 9) {
            return 1 * 9;
        } else if (nbWarn <= 18) {
            return 2 * 9;
        } else if (nbWarn <= 27) {
            return 3 * 9;
        } else if (nbWarn <= 36) {
            return 4 * 9;
        } else if (nbWarn <= 45) {
            return 5 * 9;
        } else {
            return 6 * 9;
        }
    }

}
