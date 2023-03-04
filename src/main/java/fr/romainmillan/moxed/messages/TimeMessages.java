package fr.romainmillan.moxed.messages;

public enum TimeMessages {
    SWITCH_TIME_MESSAGE(MoxedMessage.PREFIX_NORMAL.getMessage() + "Le temps vient d'être mis sur §9");

    private String messages;

    TimeMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
