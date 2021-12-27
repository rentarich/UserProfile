package si.fri.rso.userprofile.api.v1.resources;


/*import com.kumuluz.ee.cors.annotations.CrossOrigin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;*/


import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.cdi.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.eclipse.jetty.server.Authentication;
import si.fri.rso.userprofile.models.Borrow;
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
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Log
@CrossOrigin
public class UserProfileResource {

    private Client httpClient;
    private String baseUrl;

    private com.kumuluz.ee.logs.Logger logger = LogManager.getLogger(UserProfileResource.class.getName());
    private Logger log=Logger.getLogger(UserProfileResource.class.getName());

    @Inject
    private UserBean userBean;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }


    @GET
    @Operation(summary = "Get single user with id.", description = "Returns requested user.", tags = "persons")
    @ApiResponses({
            @ApiResponse(description = "Person with declared id", responseCode = "200", content = @Content(schema = @Schema(implementation =
                    Person.class))),
            @ApiResponse(responseCode = "405", description = "Validation error."),
            @ApiResponse(responseCode = "404", description = "Not Found.")
    })
    @Path("{userId}")
    public Response getUser(@PathParam("userId") Integer userId){
        Person p =  userBean.getPerson(userId);
        logger.info("get user"+userId);
        if(p != null) {
//            p.setBorrows(null);

            return Response.status(Response.Status.OK).entity(p).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Operation(summary = "Get borrows for person with id.", description = "Returns borrows of requested user.", tags = "persons")
    @ApiResponses({
            @ApiResponse(description = "List of borrows for user", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation =
                    Borrow.class)))),
            @ApiResponse(responseCode = "405", description = "Validation error."),
            @ApiResponse(responseCode = "404", description = "Not Found.")
    })
    @Path("{userId}/borrows")
    public Response getbororows(@PathParam("userId") Integer userId){
        logger.info("get borrowsfor user "+userId);
        List<Borrow> p = userBean.getBorrows(userId);
        if(p != null) {
            return Response.status(Response.Status.OK).entity(p).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Operation(description = "Update user.", summary = "Updating user",
            tags = "persons",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item updated."),
                    @ApiResponse(responseCode = "404", description = "Not Found.")

            })
    @Path("{userId}")
    public Response updateUser(@PathParam("userId") Integer userId, Person person) {
        person = userBean.updateUser(userId, person);

        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();
    }




}
