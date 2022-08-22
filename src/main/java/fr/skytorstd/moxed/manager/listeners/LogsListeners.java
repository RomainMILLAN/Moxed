package fr.skytorstd.moxed.manager.listeners;

import fr.skytorstd.moxed.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class LogsListeners implements Listener {
    public Main main;
    public LogsListeners(Main main) {
        this.main = main;
    }

    //JOIN AND QUIT
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("JoinorQuit", "joinandquit", p.getName(), "HAS JOIN THE SERVER");
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("JoinorQuit", "joinandquit", p.getName(), "HAS LEFT THE SERVER");
    }

    //MOVE
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Move", "move", p.getName(), "HAS MOVE FROM " + e.getFrom().toString() + " TO " + e.getTo().toString());
    }

    //BLOCKS
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("PlaceandBreak", "placeandbreak", p.getName(), "HAS PLACE A BLOCK (" + e.getBlockPlaced().getType().name() + ") AT " + e.getBlockPlaced().getLocation().getX() + "|" + e.getBlockPlaced().getLocation().getY() + "|" + e.getBlockPlaced().getLocation().getZ());
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("PlaceandBreak", "placeandbreak", p.getName(), "HAS BREAK A BLOCK (" + e.getBlock().getType().name() + ") AT " + e.getBlock().getLocation().getX() + "|" + e.getBlock().getLocation().getY() + "|" + e.getBlock().getLocation().getZ());
    }

    //CHAT
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Chat", "chat", p.getName(), "HAS WRITE : " + e.getMessage().toString());
    }

    //MODERATION
    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Moderation", "moderation", p.getName(), "HAS CHANGE OF WORLD FROM " + e.getFrom().getName() + " TO " + e.getPlayer().getWorld().getName());
    }
    @EventHandler
    public void onChangeGameMode(PlayerGameModeChangeEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Moderation", "moderation", p.getName(), "HAS CHANGE GAMEMODE TO " + e.getNewGameMode());
    }
    @EventHandler
    public void onKick(PlayerKickEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Moderation", "moderation", p.getName(), "HAS KICK FOR " + e.getReason());
    }
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Moderation", "moderation", p.getName(), "HAS TELEPORT FROM " + e.getFrom().getX() + "|" + e.getFrom().getY() + "|" + e.getFrom().getZ() + " TO " + e.getTo().getX() + "|" + e.getTo().getY() + "|" + e.getTo().getZ());
    }
    @EventHandler
    public void onToogleFlight(PlayerToggleFlightEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Moderation", "moderation", p.getName(), "HAS TOGGLE FLIGHT");
    }

    //OTHERS
    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Logs", "status", p.getName(), "HAS DROP ITEM " + e.getItemDrop().getName() + " AT " + e.getItemDrop().getLocation().getX() + "|" + e.getItemDrop().getLocation().getY() + "|" + e.getItemDrop().getLocation().getZ());
    }
    @EventHandler
    public void onEditBook(PlayerEditBookEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("Logs", "status", p.getName(), "HAS EDIT BOOK");
    }

    //SPAWN AND RESPAWN
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("SpawnandRespawn", "spawnandrespawn", p.getName(), "HAS RESPAWN AT " + e.getRespawnLocation().getZ() + "|" + e.getRespawnLocation().getY() + "|" + e.getRespawnLocation().getZ());
    }
    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent e){
        Player p = (Player) e.getPlayer();
        main.writeOnLogs("SpawnandRespawn", "spawnandrespawn", p.getName(), "HAS SPAWN AT " + e.getSpawnLocation().getX() + "|" + e.getSpawnLocation().getY() + "|" + e.getSpawnLocation().getZ());
    }
}
