package club.cheapok.service;

import club.cheapok.model.User;
import club.cheapok.persistence.UserDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Path("/UserService")
public class UserService {

    private static final String SUCCESS_RESULT = "<result>success</result>";

    private static final String FAILURE_RESULT = "<result>failure</result>";

    private final UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @GET
    @Path("/users")
    @Produces(APPLICATION_XML)
    public List<User> getUsers() {
        return userDao.getAllUsers();
    }

    @GET
    @Path("/users/{userId}")
    @Produces(APPLICATION_XML)
    public User getUser(@PathParam("userId") final int id) {
        return userDao.getUser(id);
    }

    @POST
    @Path("/users")
    @Produces(APPLICATION_XML)
    @Consumes(APPLICATION_FORM_URLENCODED)
    public String createUser(@FormParam("id") int id,
                             @FormParam("name") String name,
                             @FormParam("profession") String profession) {
        return userDao.addUser(new User(id, name, profession)) ?
                SUCCESS_RESULT : FAILURE_RESULT;
    }

    @PUT
    @Path("/users")
    @Produces(APPLICATION_XML)
    @Consumes(APPLICATION_FORM_URLENCODED)
    public String updateUser(@FormParam("id") int id,
                             @FormParam("name") String name,
                             @FormParam("profession") String profession) {
        return userDao.updateUser(new User(id, name, profession)) ?
                SUCCESS_RESULT : FAILURE_RESULT;
    }

    @DELETE
    @Path("/users/{userId}")
    @Produces(APPLICATION_XML)
    public String deleteUser(@PathParam("userId") final int id) {
        return userDao.deleteUser(id) ? SUCCESS_RESULT : FAILURE_RESULT;
    }

    @OPTIONS
    @Path("/users")
    @Produces(APPLICATION_XML)
    public String getSupportedOperations() {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }
}
