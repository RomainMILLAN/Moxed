package fr.romainmillan.moxed.messages;

public enum StaffMessages {
    STAFF_ACTIVATE(MoxedMessage.PREFIX_NORMAL.getMessage() + "Le mode staff viens d'être §9Activé"),
    STAFF_DESACTIVATE(MoxedMessage.PREFIX_NORMAL.getMessage() + "Le mode staff viens d'être §9Désactivé");

    private String message;

    StaffMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
