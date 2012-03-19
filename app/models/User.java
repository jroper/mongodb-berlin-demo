package models;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

public class User {
    @Id
    private String id = new ObjectId().toString();
    private String name;
    private String email;
    private int updates;
    private Set<String> friendIds = new HashSet<String>();

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(Set<String> friendIds) {
        this.friendIds = friendIds;
    }

    public int getUpdates() {
        return updates;
    }

    public void setUpdates(int updates) {
        this.updates = updates;
    }
}
