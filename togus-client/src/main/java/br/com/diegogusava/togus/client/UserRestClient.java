package br.com.diegogusava.togus.client;

import br.com.diegogusava.togus.dto.UserDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(ApiVersion.Version + "/users")
@Consumes(MediaType.APPLICATION_JSON)
public interface UserRestClient {

    @GET
    @Path("/")
    Response findAll();

    @GET
    @Path("/{id}")
    Response findById(@PathParam("id") Integer id);

    @GET
    @Path("/{id}")
    @Produces("application/vdn.diegogusava.user.complete+json")
    Response findByIdComplete(@PathParam("id") Integer id);

    @POST
    @Path("/")
    Response add(@Context UriInfo uriInfo, UserDTO user);

    @PUT
    @Path("/{id}")
    Response put(@PathParam("id") Integer id, UserDTO user);

    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Integer id);

}
