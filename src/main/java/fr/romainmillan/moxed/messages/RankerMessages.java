package fr.romainmillan.moxed.messages;

public enum RankerMessages {
    KICK_PLAYER_AFTER_SET_RANK(MoxedMessage.PREFIX_NORMAL.getMessage() + "Merci de vous reconnecter");

    private String messages;

    RankerMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
