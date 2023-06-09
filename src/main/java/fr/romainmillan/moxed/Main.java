package fr.romainmillan.moxed;

import fr.romainmillan.moxed.commands.*;
import fr.romainmillan.moxed.listeners.ChatListener;
import fr.romainmillan.moxed.listeners.FreezeListener;
import fr.romainmillan.moxed.listeners.LogListener;
import fr.romainmillan.moxed.listeners.MaintenanceListener;
import fr.romainmillan.moxed.listeners.ModerationListener;
import fr.romainmillan.moxed.listeners.RankerListener;
import fr.romainmillan.moxed.object.Ticket;
import fr.romainmillan.moxed.state.Ranks;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public final class Main extends JavaPlugin {
    public static HashMap<Player, Ranks> rankPlayer = new HashMap<Player, Ranks>();
    public static ArrayList<Ticket> tickets = new ArrayList<>();
    public static ArrayList<Player> staffPlayer = new ArrayList<>();
    public static ArrayList<Player> freezePlayer = new ArrayList<>();
    public static ArrayList<Player> mutePlayer = new ArrayList<>();
    public boolean Maintenance = false;

    /*
     * ENABLE/DISABLE
     */
    @Override
    public void onEnable() {

        System.out.println("\n" +
                " __    __     ______     __  __     ______     _____    \n" +
                "/\\ \"-./  \\   /\\  __ \\   /\\_\\_\\_\\   /\\  ___\\   /\\  __-.  \n" +
                "\\ \\ \\-./\\ \\  \\ \\ \\/\\ \\  \\/_/\\_\\/_  \\ \\  __\\   \\ \\ \\/\\ \\ \n" +
                " \\ \\_\\ \\ \\_\\  \\ \\_____\\   /\\_\\/\\_\\  \\ \\_____\\  \\ \\____- \n" +
                "  \\/_/  \\/_/   \\/_____/   \\/_/\\/_/   \\/_____/   \\/____/ \n" +
                "                                                        ");

        // Config & Files
        saveDefaultConfig();
        createFile("ranker");
        createFile("warns");
        createLogs("Logs");
        createLogs("JoinorQuit");
        createLogs("Move");
        createLogs("PlaceandBreak");
        createLogs("Chat");
        createLogs("Moderation");
        createLogs("SpawnandRespawn");

        // Commands
        getCommand("ranker").setExecutor(new commandRanker(this));
        getCommand("gm").setExecutor(new commandGM(this));
        getCommand("day").setExecutor(new commandTime(this));
        getCommand("night").setExecutor(new commandTime(this));
        getCommand("spawn").setExecutor(new commandSpawn(this));
        getCommand("dm").setExecutor(new commandDM(this));
        getCommand("mp").setExecutor(new commandDM(this));
        getCommand("staff").setExecutor(new commandStaff(this));
        getCommand("mm").setExecutor(new commandModeration(this));
        getCommand("freeze").setExecutor(new commandFreeze(this));
        getCommand("system").setExecutor(new commandSystem(this));
        getCommand("cc").setExecutor(new commandChat(this));
        getCommand("warns").setExecutor(new commandWarn(this));
        getCommand("ticket").setExecutor(new commandTicket(this));
        getCommand("maintenance").setExecutor(new commandMaintenance(this));

        // Listeners
        getServer().getPluginManager().registerEvents(new RankerListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new ModerationListener(this), this);
        getServer().getPluginManager().registerEvents(new FreezeListener(this), this);
        getServer().getPluginManager().registerEvents(new LogListener(this), this);
        getServer().getPluginManager().registerEvents(new MaintenanceListener(this), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[MOXED] Extinction du plugin");
    }

    /*
     * FILES
     */
    public void createFile(String fileName) {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile(String fileName) {
        return new File(getDataFolder(), fileName + ".yml");
    }

    public void createLogs(String fileName) {
        File directory = new File(getDataFolder() + "/Logs");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File logs = new File(getDataFolder() + "/Logs/" + fileName + ".txt");
        if (!logs.exists()) {
            try {
                logs.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getLogs(String fileName) {
        return new File(getDataFolder() + "/Logs", fileName + ".txt");
    }

    public void writeOnLogs(String fileName, String configName, String pseudoName, String logsString) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(getDataFolder() + "/Logs/" + fileName + ".txt", true);
            Calendar cal = Calendar.getInstance();
            fw.write(cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR)
                    + " - " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":"
                    + cal.get(Calendar.MILLISECOND) + " | " + pseudoName + " | " + logsString + "\n");
            fw.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
