package fr.skytorstd.moxed.commands;

import fr.skytorstd.moxed.Main;
import fr.skytorstd.moxed.MoxedPermissions;
import fr.skytorstd.moxed.manager.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.io.IOException;

public class commandRanker implements CommandExecutor {
    public Main main;

    public commandRanker(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("ranker")) {
            if (sender.hasPermission(new MoxedPermissions().ranker) || sender.isOp()) {
                String commandAtUse = "/ranker <set/see> [player] [groupe]";
                if (args.length <= 1) {
                    sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("see")) {
                        Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                        if (targetPlayer == null) {
                            sender.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                            return false;
                        }
                        FileConfiguration playersranks = YamlConfiguration.loadConfiguration(main.getFile("RankerPlayerRank"));
                        if (playersranks.contains("Players." + targetPlayer.getName())) {
                            String group = String.valueOf(playersranks.get("Players." + targetPlayer.getName()));
                            sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getDisplayName() + " §fest dans le groupe " + main.getConfig().getString("Ranker." + group + ".prefix") + group);
                            return true;
                        } else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Ce joueur n'est pas contenue dans la Base de Données !");
                            return false;
                        }
                    }else {
                        sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }else if(args.length == 3){
                    if(args[0].equalsIgnoreCase("set")) {
                        if (main.getConfig().contains("Ranker." + args[2])) {

                            Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                            if (targetPlayer == null) {
                                sender.sendMessage(Messages.ERRORMESSAGE_PLAYER_NOT_CONNECTED.getMessage());
                                return false;
                            }

                            if (args[2].equalsIgnoreCase("administrateur")) {
                                Main.administrateur.add(targetPlayer);

                                if(Main.responsables.contains(targetPlayer)){
                                    Main.responsables.remove(targetPlayer);
                                }
                                if(Main.moderateur.contains(targetPlayer)){
                                    Main.moderateur.remove(targetPlayer);
                                }

                                FileConfiguration playersranks = YamlConfiguration.loadConfiguration(main.getFile("RankerPlayerRank"));
                                playersranks.set("Players." + targetPlayer.getName(), "administrateur");
                                try {
                                    playersranks.save(main.getFile("RankerPlayerRank"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (Main.administrateur.contains(targetPlayer)) {
                                    sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getDisplayName() + " §fa été ajouté à la liste des " + main.getConfig().getString("Ranker.administrateur.prefix") + "Administrateur");
                                    return true;
                                } else {
                                    sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Une erreur c'est produite, veuilliez reessayer !");
                                    return false;
                                }
                            } else if (args[2].equalsIgnoreCase("responsable")) {
                                Main.responsables.add(targetPlayer);

                                if(Main.administrateur.contains(targetPlayer)){
                                    Main.administrateur.remove(targetPlayer);
                                }
                                if(Main.moderateur.contains(targetPlayer)){
                                    Main.moderateur.remove(targetPlayer);
                                }

                                FileConfiguration playersranks = YamlConfiguration.loadConfiguration(main.getFile("RankerPlayerRank"));
                                playersranks.set("Players." + targetPlayer.getName(), "responsable");
                                try {
                                    playersranks.save(main.getFile("RankerPlayerRank"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (Main.responsables.contains(targetPlayer)) {
                                    sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getDisplayName() + " §fa été ajouté à la liste des " + main.getConfig().getString("Ranker.responsable.prefix") + "Responsable");
                                    return true;
                                } else {
                                    sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Une erreur c'est produite, veuilliez reessayer !");
                                    return false;
                                }
                            } else if (args[2].equalsIgnoreCase("moderateur")) {
                                Main.moderateur.add(targetPlayer);

                                if(Main.administrateur.contains(targetPlayer)){
                                    Main.administrateur.remove(targetPlayer);
                                }
                                if(Main.responsables.contains(targetPlayer)){
                                    Main.responsables.remove(targetPlayer);
                                }

                                FileConfiguration playersranks = YamlConfiguration.loadConfiguration(main.getFile("RankerPlayerRank"));
                                playersranks.set("Players." + targetPlayer.getName(), "moderateur");
                                try {
                                    playersranks.save(main.getFile("RankerPlayerRank"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (Main.moderateur.contains(targetPlayer)) {
                                    sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getDisplayName() + " §fa été ajouté à la liste des " + main.getConfig().getString("Ranker.moderateur.prefix") + "Moderateur");
                                    return true;
                                } else {
                                    sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Une erreur c'est produite, veuilliez reessayer !");
                                    return false;
                                }
                            } else if (args[2].equalsIgnoreCase("joueur")) {

                                if(Main.administrateur.contains(targetPlayer)){
                                    Main.administrateur.remove(targetPlayer);
                                }
                                if(Main.moderateur.contains(targetPlayer)){
                                    Main.moderateur.remove(targetPlayer);
                                }
                                if(Main.moderateur.contains(targetPlayer)){
                                    Main.moderateur.remove(targetPlayer);
                                }

                                FileConfiguration playersranks = YamlConfiguration.loadConfiguration(main.getFile("RankerPlayerRank"));
                                playersranks.set("Players." + targetPlayer.getName(), "NULL");
                                try {
                                    playersranks.save(main.getFile("RankerPlayerRank"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (!Main.administrateur.contains(targetPlayer) && !Main.responsables.contains(targetPlayer) && !Main.moderateur.contains(targetPlayer)) {
                                    sender.sendMessage(Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + targetPlayer.getDisplayName() + " §fa été ajouté à la liste des " + main.getConfig().getString("Ranker.joueur.prefix") + "Joueur");
                                    return true;
                                } else {
                                    sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Une erreur c'est produite, veuilliez reessayer !");
                                    return false;
                                }
                            } else {
                                sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Le groupe que vous avez specifié n'existe pas !");
                                return false;
                            }
                        } else {
                            sender.sendMessage(Messages.PREFIX_ERRROR.getMessage() + "Le groupe que vous avez specifié n'existe pas dans la configuration !");
                            return false;
                        }
                    }else {
                        sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                        return false;
                    }
                }else {
                    sender.sendMessage(Messages.PREFIX_ERRORCMD.getMessage() + commandAtUse);
                    return false;
                }
            } else {
                sender.sendMessage(Messages.PREFIX_ERRORPERM.getMessage());
                return false;
            }
        }

        return false;
    }
}
