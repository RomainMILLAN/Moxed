package fr.romainmillan.moxed.object;


public class Ticket {
    private int id;
    private String author;
    private String description;

    public Ticket(int id, String author, String description){
        this.id = id;
        this.author = author;
        this.description = description;
    }


    public int getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

}
