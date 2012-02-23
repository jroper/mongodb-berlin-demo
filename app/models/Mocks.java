package models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Mocks {
    public static final User JAMES;
    public static final User MARK;
    public static final List<User> USERS = new ArrayList<User>();
    public static final List<StatusUpdate> FEED = new ArrayList<StatusUpdate>();
    
    
    static {
        JAMES = new User(new ObjectId(1, 2, 3).toString());
        JAMES.setName("James Roper");
        JAMES.setEmail("james@jazzy.id.au");
        MARK = new User(new ObjectId(4, 5, 6).toString());
        MARK.setName("Mark Zuckerberg");
        MARK.setEmail("mark@facebook.com");
        JAMES.getFriendIds().add(MARK.getId());
        USERS.add(JAMES);
        USERS.add(MARK);
        
        StatusUpdate update = new StatusUpdate();
        update.setAuthor(JAMES);
        update.setText("Hello world!");
        
        FEED.add(update);
    }
    
}
