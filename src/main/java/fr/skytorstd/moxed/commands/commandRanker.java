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
import java.util.ArrayList;

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

                            ArrayList<String> administrateurStringList = new ArrayList<>();
                            administrateurStringList.add("admin");
                            administrateurStringList.add("administrateur");
                            ArrayList<String> responsableStringList = new ArrayList<>();
                            responsableStringList.add("resp");
                            responsableStringList.add("responsable");
                            ArrayList<String> moderateurStringList = new ArrayList<>();
                            moderateurStringList.add("modo");
                            moderateurStringList.add("moderateur");
                            ArrayList<String> joueurStringList = new ArrayList<>();
                            joueurStringList.add("joueur");

                            if (administrateurStringList.contains(args[2])) {
                                sender.sendMessage(this.setGroupeForPlayer("administrateur", targetPlayer));
                                return true;

                            } else if (responsableStringList.contains(args[2])) {
                                sender.sendMessage(this.setGroupeForPlayer("responsable", targetPlayer));
                                return true;

                            } else if (moderateurStringList.contains(args[2])) {
                                sender.sendMessage(this.setGroupeForPlayer("moderateur", targetPlayer));
                                return true;

                            } else if (joueurStringList.contains(args[2])) {
                                sender.sendMessage(this.setGroupeForPlayer("joueur", targetPlayer));
                                return true;

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

    private String setGroupeForPlayer(String groupe, Player p){
        FileConfiguration playersranks = YamlConfiguration.loadConfiguration(main.getFile("RankerPlayerRank"));
        if(groupe.equalsIgnoreCase("administrateur")){
            if(!(Main.administrateur.contains(p))){
                Main.administrateur.add(p);
            }

            if(Main.responsables.contains(p)){
                Main.responsables.remove(p);
            }
            if(Main.moderateur.contains(p)){
                Main.moderateur.remove(p);
            }
            playersranks.set("Players." + p.getName(), "administrateur");
        }else if(groupe.equalsIgnoreCase("responsable")){
            if(!Main.responsables.contains(p)){
                Main.responsables.add(p);
            }

            if(Main.administrateur.contains(p)){
                Main.administrateur.remove(p);
            }
            if(Main.moderateur.contains(p)){
                Main.moderateur.remove(p);
            }
            playersranks.set("Players." + p.getName(), "responsable");
        }else if(groupe.equalsIgnoreCase("moderateur")){
            if(!(Main.moderateur.contains(p))){
                Main.moderateur.add(p);
            }

            if(Main.administrateur.contains(p)){
                Main.administrateur.remove(p);
            }
            if(Main.responsables.contains(p)){
                Main.responsables.remove(p);
            }
            playersranks.set("Players." + p.getName(), "moderateur");
        }else if(groupe.equalsIgnoreCase("joueur")) {
            if(Main.administrateur.contains(p)){
                Main.administrateur.remove(p);
            }
            if(Main.moderateur.contains(p)){
                Main.moderateur.remove(p);
            }
            if(Main.moderateur.contains(p)){
                Main.moderateur.remove(p);
            }
            playersranks.set("Players." + p.getName(), "NULL");
        }

        try {
            playersranks.save(main.getFile("RankerPlayerRank"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Main.administrateur.contains(p)) {
            return Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + p.getDisplayName() + " §fa été ajouté à la liste des " + main.getConfig().getString("Ranker.administrateur.prefix") + "Administrateur";
        }else if (Main.responsables.contains(p)) {
            return Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + p.getDisplayName() + " §fa été ajouté à la liste des " + main.getConfig().getString("Ranker.responsable.prefix") + "Responsable";
        }else if (Main.moderateur.contains(p)) {
            return Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + p.getDisplayName() + " §fa été ajouté à la liste des " + main.getConfig().getString("Ranker.moderateur.prefix") + "Moderateur";
        }else if (!Main.administrateur.contains(p) && !Main.responsables.contains(p) && !Main.moderateur.contains(p)) {
            return Messages.PREFIX_NORMAL.getMessage() + "Le joueur §6" + p.getDisplayName() + " §fa été ajouté à la liste des " + main.getConfig().getString("Ranker.joueur.prefix") + "Joueur";
        } else {
            return Messages.PREFIX_ERRROR.getMessage() + "Une erreur c'est produite, veuilliez reessayer !";
        }
    }
}
