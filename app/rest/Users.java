package rest;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.sun.jersey.spi.resource.Singleton;
import models.Mocks;
import models.StatusUpdate;
import models.User;
import net.vz.mongodb.jackson.DBQuery;
import net.vz.mongodb.jackson.DBUpdate;
import net.vz.mongodb.jackson.JacksonDBCollection;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.List;

@Path("/rest/users")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class Users {
    private final JacksonDBCollection<User, String> usersDb;
    private final JacksonDBCollection<StatusUpdate, ObjectId> feedDb;


    public Users() throws UnknownHostException {
        DB db = new Mongo().getDB("mongovz");
        usersDb = JacksonDBCollection.wrap(db.getCollection("users"), User.class, String.class);
        feedDb = JacksonDBCollection.wrap(db.getCollection("feed"), StatusUpdate.class, ObjectId.class, StatusUpdate.DbView.class);
    }

    @GET
    public List<User> list() {
        return usersDb.find().sort(new BasicDBObject("name", 1)).limit(10).toArray();
    }
    
    @POST
    public User create(User user) {
        return usersDb.save(user).getSavedObject();
    }
    
    @GET
    @Path("/{userId}")
    public User get(@PathParam("userId") String userId) {
        return usersDb.findOneById(userId);
    }
    
    @POST
    @Path("/{userId}/friends/{friendId}")
    public Response addFriend(@PathParam("userId") String userId, @PathParam("friendId") String friendId) {
        usersDb.updateById(userId, DBUpdate.addToSet("friendIds", friendId));
        return Response.noContent().build();
    }

    @POST
    @Path("/{userId}/userFeed")
    public Response updateStatus(@PathParam("userId") String userId, StatusUpdate update) {
        update.setAuthor(get(userId));
        feedDb.save(update);
        usersDb.updateById(userId, DBUpdate.inc("updates"));
        return Response.noContent().build();
    }

    @GET
    @Path("/{userId}/feed")
    public List<StatusUpdate> getFeed(@PathParam("userId") String userId) {
        return feedDb.find().or(DBQuery.in("authorId.$id", get(userId).getFriendIds()), DBQuery.is("authorId.$id", userId))
                .sort(new BasicDBObject("_id", -1)).toArray();
    }
}
