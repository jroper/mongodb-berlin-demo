package rest;

import models.Mocks;
import models.StatusUpdate;
import models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/rest/users")
@Produces(MediaType.APPLICATION_JSON)
public class Users {
    @GET
    public List<User> list() {
        return Mocks.USERS;
    }
    
    @POST
    public User create(User user) {
        Mocks.USERS.add(user);
        return user;
    }
    
    @GET
    @Path("/{userId}")
    public User get(@PathParam("userId") String userId) {
        for (User user : Mocks.USERS) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    
    @POST
    @Path("/{userId}/friends/{friendId}")
    public Response addFriend(@PathParam("userId") String userId, @PathParam("friendId") String friendId) {
        User user = get(userId);
        user.getFriendIds().add(friendId);
        user.setUpdates(user.getUpdates() + 1);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{userId}/userFeed")
    public List<StatusUpdate> getUserFeed(@PathParam("userId") String userId) {
        return Mocks.FEED;
    }

    @POST
    @Path("/{userId}/userFeed")
    public Response updateStatus(@PathParam("userId") String userId, StatusUpdate update) {
        update.setAuthor(get(userId));
        Mocks.FEED.add(0, update);
        return Response.noContent().build();
    }

    @GET
    @Path("/{userId}/feed")
    public List<StatusUpdate> getFeed(@PathParam("userId") String userId) {
        return Mocks.FEED;
    }
}
