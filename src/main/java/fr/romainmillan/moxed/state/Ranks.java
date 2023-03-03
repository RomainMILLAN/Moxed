package fr.romainmillan.moxed.state;

public enum Ranks {
    ADMINISTRATEUR("Administrateur"),
    RESPONSABLE("Responsable"),
    MODERATEUR("Moderateur"),
    JOUEUR("Joueur");

    private String ranks;

    Ranks(String ranks) {
        this.ranks = ranks;
    }

    public String getRanks() {
        return ranks;
    }
}
