package fr.romainmillan.moxed.service;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.messages.RankerMessages;
import fr.romainmillan.moxed.state.Ranks;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

public class RankerService {


    /**
     * Retourne le rangs du joueur passer en paramètre.
     * <pre/>
     *
     * @param p
     * @return <code>Ranks</code> si le joueur est contenue dans la liste des rangs, <code>Ranks.JOUEURS</code> sinon
     */
    public static Ranks getRanksPlayer(Player p){

        if(!Main.rankPlayer.containsKey(p)){
            return Ranks.JOUEUR;
        }

        return Main.rankPlayer.get(p);
    }

    /**
     * Retourne un String coloré du rangs de la personne
     * <pre/>
     *
     * @param p
     * @return <code>String</code> coloré
     */
    public static String getRanksPlayerWithColor(Player p){
        Ranks rankPlayer = getRanksPlayer(p);

        return getRanksWithRanksState(rankPlayer);
    }

    /**
     * Retourne le String coloré du ranks passer en parametre
     * <pre/>
     *
     * @param r
     * @return <code>String</code> coloré
     */
    public static String getRanksWithRanksState(Ranks r){

        switch (r){
            case ADMINISTRATEUR:
                return "§cAdministrateur";
            case RESPONSABLE:
                return "§6Responsable";
            case MODERATEUR:
                return "§bModerateur";
            default:
                return "§eJoueur";
        }
    }

    /**
     * Permet de set le rank du player passer en parametre.
     * <pre/>
     *
     * @param p
     * @param r
     * @param main
     */
    public static void setRanksToPlayer(Player p, Ranks r, Main main){
        FileConfiguration rankers = YamlConfiguration.loadConfiguration(main.getFile("ranker"));

        rankers.set("Players." + p.getName(), r.getRanks());

        System.out.println(r.getRanks());

        try {
            rankers.save(main.getFile("ranker"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        p.kickPlayer(RankerMessages.KICK_PLAYER_AFTER_SET_RANK.getMessages());
    }

    /**
     * Permet de set le rangs d'un joueur courrant
     * <pre/>
     *
     * @param p
     * @param r
     * @param main
     */
    public static void setRankToPlayerCurrent(Player p, Ranks r, Main main){
        main.rankPlayer.put(p, r);
    }

    /**
     * Permet de savoir si un joueur passer en paramètre est un Administrateur
     * <pre/>
     *
     * @param p
     * @return <code>true</code> si le joueur est un administrateur, <code>false</code> sinon
     */
    public static boolean isAdmin(Player p){
        if(Main.rankPlayer.containsKey(p) && Main.rankPlayer.get(p) == Ranks.ADMINISTRATEUR)
            return true;

        return false;
    }

}
