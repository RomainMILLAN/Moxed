package fr.skytorstd.moxed;

import fr.skytorstd.moxed.commands.*;
import fr.skytorstd.moxed.manager.listeners.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public final class Main extends JavaPlugin {
    public static ArrayList<Player> administrateur = new ArrayList<>();
    public static ArrayList<Player> responsables = new ArrayList<>();
    public static ArrayList<Player> moderateur = new ArrayList<>();
    public static ArrayList<Player> Staff = new ArrayList<Player>();
    public static ArrayList<Player> Mute = new ArrayList<Player> ();
    public static ArrayList<Player> Freeze = new ArrayList<Player> ();
    public static HashMap<Player, String> Tickets = new HashMap<>();
    public static boolean MAINTENANCE = false;



    /*
     * ENABLE/DISABLE
     */
    @Override
    public void onEnable() {
        if(getConfig().getString("maintenance").equalsIgnoreCase("off")){
            MAINTENANCE = false;
        }else {
            MAINTENANCE = true;
        }

        System.out.println("\n" +
                " __    __     ______     __  __     ______     _____    \n" +
                "/\\ \"-./  \\   /\\  __ \\   /\\_\\_\\_\\   /\\  ___\\   /\\  __-.  \n" +
                "\\ \\ \\-./\\ \\  \\ \\ \\/\\ \\  \\/_/\\_\\/_  \\ \\  __\\   \\ \\ \\/\\ \\ \n" +
                " \\ \\_\\ \\ \\_\\  \\ \\_____\\   /\\_\\/\\_\\  \\ \\_____\\  \\ \\____- \n" +
                "  \\/_/  \\/_/   \\/_____/   \\/_/\\/_/   \\/_____/   \\/____/ \n" +
                "                                                        ");
        System.out.println("[MOXED] Lancement du contrôle plugin");

        saveDefaultConfig();
        createFile("RankerPlayerRank");
        createFile("Warns");
        //LOGS
        createLogs("Logs");
        createLogs("JoinorQuit");
        createLogs("Move");
        createLogs("PlaceandBreak");
        createLogs("Chat");
        createLogs("Moderation");
        createLogs("SpawnandRespawn");

        getServer().getPluginManager().registerEvents(new JoinQuitEventListeners(this), this);
        getServer().getPluginManager().registerEvents(new ChatListeners(this), this);
        getServer().getPluginManager().registerEvents(new ClickListeners(this), this);
        getServer().getPluginManager().registerEvents(new ModiListeners(this), this);
        getServer().getPluginManager().registerEvents(new LogsListeners(this), this);

        getCommand("ranker").setExecutor(new commandRanker(this));
        getCommand("maintenance").setExecutor(new commandMaintenance(this));
        getCommand("staff").setExecutor(new commandStaff(this));
        getCommand("moderation").setExecutor(new commandModeration(this));
        getCommand("gm").setExecutor(new commandGM(this));
        getCommand("day").setExecutor(new commandTime(this));
        getCommand("night").setExecutor(new commandTime(this));
        getCommand("freeze").setExecutor(new commandFreeze(this));
        getCommand("spawn").setExecutor(new commandSpawn(this));
        getCommand("dm").setExecutor(new commandDM(this));
        getCommand("mp").setExecutor(new commandDM(this));
        getCommand("logs").setExecutor(new commandLog(this));
        getCommand("warn").setExecutor(new commandWarn(this));
        getCommand("ticket").setExecutor(new commandTicket(this));
    }
    @Override
    public void onDisable() {
        System.out.println("[MOXED] Extinction du plugin");
    }





    /*
     * FILES
     */
    public void createFile(String fileName){

        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public File getFile(String fileName){
        return new File(getDataFolder(), fileName + ".yml");
    }
    public void createLogs(String fileName){
        File directory = new File(getDataFolder() + "/Logs");
        if(!directory.exists()){
            directory.mkdirs();
        }
        File logs = new File(getDataFolder() + "/Logs/" + fileName + ".txt");
        if(!logs.exists()){
            try {
                logs.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public File getLogs(String fileName){
        return new File(getDataFolder() + "/Logs", fileName + ".txt");
    }
    public void writeOnLogs(String fileName, String configName, String pseudoName, String logsString){
        if(getConfig().getString("Logs.status").equalsIgnoreCase("on") && getConfig().getString("Logs." + configName).equalsIgnoreCase("on")){
            FileWriter fw = null;
            try {
                fw = new FileWriter(getDataFolder() + "/Logs/" + fileName + ".txt",true);
                Calendar cal = Calendar.getInstance();
                fw.write(cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR) + " - " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.MILLISECOND) + " | " + pseudoName + " | " + logsString + "\n");
                fw.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

}
