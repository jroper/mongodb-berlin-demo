package models;

import net.vz.mongodb.jackson.DBRef;
import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonView;

import java.util.Date;

public class StatusUpdate {
    public static class DbView {}
    public static class WebView {}

    @JsonView(DbView.class)
    private ObjectId _id;
    private String text;
    @JsonView(DbView.class)
    private DBRef<User, String> authorId;

    @JsonView(WebView.class)
    public String getId() {
        return _id.toString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonView(WebView.class)
    public User getAuthor() {
        return authorId.fetch();
    }

    public void setAuthor(User author) {
        this.authorId = new DBRef<User, String>(author.getId(), "users");
    }

    @JsonView(WebView.class)
    public Date getDate() {
        return new Date(_id.getTime());
    }
}
