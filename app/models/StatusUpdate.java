package models;

import org.bson.types.ObjectId;

import java.util.Date;

public class StatusUpdate {
    private String id = new ObjectId().toString();
    private String text;
    private User author;
    private Date date = new Date();

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
