package fr.romainmillan.moxed.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private Connection connection;
    private final String urlLinkToBD = "jdbc:sqlite:MoxedDB.db";

    /**
     * Retourne l'instance de la classe
     * Si l'instance n'existe pas alors elle est crée
     * @return
     */
    public static synchronized DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
        }

        return instance;
    }

    /**
     * Retourne la connection à la base de données
     * Si celle-ci n'existe pas alors elle crée la connection
     * Si une erreur survient dans la création de la connection alors il renvoye null
     * @return
     */
    public Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(this.urlLinkToBD);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        return this.connection;
    }

    /**
     * Retourne le Statement de la connection actuelle
     * @return
     */
    public Statement getStatement(){
        try {
            return DatabaseConnection.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
