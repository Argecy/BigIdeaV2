package nl.fhict.s3.restserver.endpoint;

import com.google.gson.Gson;
import nl.fhict.s3.restserver.data.UserStore;
import nl.fhict.s3.restshared.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/User")
public class SimpleRestEndpoint {

    private static final Logger log = LoggerFactory.getLogger(SimpleRestEndpoint.class);
    private static UserStore userStore = UserStore.getInstance();
    private final Gson gson;

    public SimpleRestEndpoint() {
        gson = new Gson();
    }

    @Path("/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getUser(@PathParam("user") String user) {
        log.info("GET user called for key: {}", user);
        User myResponse = userStore.getUser(user);

        return Response.status(200).entity(gson.toJson(myResponse)).build();
    }

    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllUsers() {
        log.info("GET all called");

        return Response.status(200).entity(gson.toJson(userStore.getAll())).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        log.info("POST add called for key: {}", user.getUsername());

        userStore.addUser(user);

        return Response.status(200).entity(gson.toJson(user)).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response CheckUser(User user) {
        log.info("POST login called for key: {}", user.getUsername());
        User checkeduser = userStore.getUser(user.getUsername());
        if (user.getUsername().equals(checkeduser.getUsername()) && user.getPassword().equals(checkeduser.getPassword())) {

            log.info("User succesfully authtenticated: {}", user.getUsername());
            return Response.status(200).entity(gson.toJson(checkeduser)).build();
        }else {
            log.info("user does not exist: {}", user.getUsername());
            return Response.status(401).build();
        }
    }
}
