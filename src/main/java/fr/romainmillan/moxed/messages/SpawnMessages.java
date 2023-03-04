package fr.romainmillan.moxed.messages;

public enum SpawnMessages {
    SET_SPAWN_SUCCESS(MoxedMessage.PREFIX_NORMAL.getMessage() + "Mise en place du nouveau point de spawn §9réussi"),
    TELEPORT_TO_SPAWN(MoxedMessage.PREFIX_NORMAL.getMessage() + "Téléportation au §9Spawn");

    private String messages;

    SpawnMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
