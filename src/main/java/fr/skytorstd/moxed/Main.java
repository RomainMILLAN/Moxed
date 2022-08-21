package fr.skytorstd.moxed;

import fr.skytorstd.moxed.commands.*;
import fr.skytorstd.moxed.manager.listeners.ChatListeners;
import fr.skytorstd.moxed.manager.listeners.ClickListeners;
import fr.skytorstd.moxed.manager.listeners.JoinQuitEventListeners;
import fr.skytorstd.moxed.manager.listeners.ModiListeners;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class Main extends JavaPlugin {
    public static ArrayList<Player> administrateur = new ArrayList<>();
    public static ArrayList<Player> responsables = new ArrayList<>();
    public static ArrayList<Player> moderateur = new ArrayList<>();

    //MODI
    public static ArrayList<Player> Staff = new ArrayList<Player>();
    public static ArrayList<Player> Mute = new ArrayList<Player> ();
    public static ArrayList<Player> Freeze = new ArrayList<Player> ();
    public static boolean MAINTENANCE = false;


    @Override
    public void onEnable() {
        if(getConfig().getString("maintenance").equalsIgnoreCase("off")){
            MAINTENANCE = false;
        }else {
            MAINTENANCE = true;
        }

        System.out.println("[MOXED] Lancement du contrôle du plugin");

        saveDefaultConfig();
        createFile("RankerPlayerRank");
        createFile("ModiWarp");

        getServer().getPluginManager().registerEvents(new JoinQuitEventListeners(this), this);
        getServer().getPluginManager().registerEvents(new ChatListeners(this), this);
        getServer().getPluginManager().registerEvents(new ClickListeners(this), this);
        getServer().getPluginManager().registerEvents(new ModiListeners(this), this);

        getCommand("ranker").setExecutor(new commandRanker(this));
        getCommand("maintenance").setExecutor(new commandMaintenance(this));
        getCommand("staff").setExecutor(new commandStaff(this));
        getCommand("tablist").setExecutor(new commandTablist(this));
        getCommand("tab").setExecutor(new commandTablist(this));
        getCommand("gm").setExecutor(new commandGM(this));
        getCommand("day").setExecutor(new commandTime(this));
        getCommand("night").setExecutor(new commandTime(this));
        getCommand("warp").setExecutor(new commandWarp(this));
        getCommand("freeze").setExecutor(new commandFreeze(this));
        getCommand("dm").setExecutor(new commandDM(this));
        getCommand("mp").setExecutor(new commandDM(this));
    }

    @Override
    public void onDisable() {
        System.out.println("[MOXED] Extinction du plugin");
    }




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
}
