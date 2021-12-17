package si.fri.rso.userprofile.api.v1.resources;


/*import com.kumuluz.ee.cors.annotations.CrossOrigin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;*/


import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.rso.userprofile.models.Person;
import si.fri.rso.userprofile.services.beans.UserBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
        import java.util.logging.Logger;

@ApplicationScoped
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserProfileResource {

    private Client httpClient;
    private String baseUrl;

    private Logger logger=Logger.getLogger(UserProfileResource.class.getName());

    @Inject
    private UserBean userBean;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }


    @GET
    @Path("{userId}")
    public Response getUser(@PathParam("userId") Integer userId){
        Person p =  userBean.getPerson(userId);
        p.setBorrows(null);
        return Response.status(Response.Status.OK).entity(p).build();
    }

    @GET
    @Path("{userId}/borrows")
    public Response getbororo(@PathParam("userId") Integer userId){
        return Response.status(Response.Status.OK).entity(userBean.getBorrows(userId)).build();
    }

    @PUT
    @Path("{userId}")
    public Response updateUser(@PathParam("userId") Integer userId, Person person) {
        person = userBean.updateUser(userId, person);

        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();
    }




}
