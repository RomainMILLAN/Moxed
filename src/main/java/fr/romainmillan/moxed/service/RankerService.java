package fr.romainmillan.moxed.service;

import fr.romainmillan.moxed.Moxed;
import fr.romainmillan.moxed.databases.RankerDatabase;
import fr.romainmillan.moxed.state.Ranks;
import org.bukkit.entity.Player;

public class RankerService {


    /**
     * Retourne le rangs du joueur passer en paramètre.
     * <pre/>
     *
     * @param p
     * @return <code>Ranks</code> si le joueur est contenue dans la liste des rangs, <code>Ranks.JOUEURS</code> sinon
     */
    public static Ranks getRanksPlayer(Player p){

        if(!Moxed.rank.containsKey(p)){
            return Ranks.JOUEUR;
        }

        return Moxed.rank.get(p);
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
     */
    public static void setRanksToPlayer(Player p, Ranks r){
        RankerDatabase.setRankToPlayer(p.getName(), r);
    }

}
