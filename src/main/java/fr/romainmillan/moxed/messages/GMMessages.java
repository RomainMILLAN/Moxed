package fr.romainmillan.moxed.messages;

public enum GMMessages {
    GM_DEFINED_TO(MoxedMessage.PREFIX_NORMAL.getMessage() + "Votre mode de jeu à été défini sur §9");

    private String message;

    GMMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
