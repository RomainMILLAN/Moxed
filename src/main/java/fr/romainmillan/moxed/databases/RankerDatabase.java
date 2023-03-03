package fr.romainmillan.moxed.databases;

import fr.romainmillan.moxed.service.RankerService;
import fr.romainmillan.moxed.state.Ranks;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankerDatabase {

    /**
     * Retoune le rank du joueur passer en parametre
     * <pre/>
     *
     * @param playerName
     * @return <code>Ranks</code>
     */
    public static Ranks getRankToPlayer(String playerName){
        final String sql = "SELECT ranks FROM Rank WHERE playerName='"+playerName+"'";

        try {
            ResultSet resultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(resultatSQL.next()){
                String rank = resultatSQL.getString("ranks");

                if(rank.equalsIgnoreCase(RankerService.getRanksWithRanksState(Ranks.ADMINISTRATEUR))){
                    return Ranks.ADMINISTRATEUR;
                }else if(rank.equalsIgnoreCase(RankerService.getRanksWithRanksState(Ranks.RESPONSABLE))){
                    return Ranks.RESPONSABLE;
                }else if(rank.equalsIgnoreCase(RankerService.getRanksWithRanksState(Ranks.MODERATEUR))){
                    return Ranks.MODERATEUR;
                }else {
                    return Ranks.JOUEUR;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Permet de set un rank à un player
     * <pre/>
     *
     * @param playerName
     * @param rank
     */
    public static void setRankToPlayer(String playerName, Ranks rank){
        String sql = "";
        if(getRankToPlayer(playerName) == null){
            sql = "INSERT INTO Rank(playerName, ranks) VALUES('"+playerName+"', '"+rank.getRanks()+"')";
        }else{
            sql = "UPDATE Rank SET ranks='"+rank.getRanks()+"' WHERE playerName='"+playerName+"'";
        }

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
