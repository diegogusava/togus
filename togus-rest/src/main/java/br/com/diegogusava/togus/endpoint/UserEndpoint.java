package br.com.diegogusava.togus.endpoint;

import br.com.diegogusava.togus.domain.User;
import br.com.diegogusava.togus.dto.UserDTO;
import br.com.diegogusava.togus.repository.UserRepository;
import io.swagger.annotations.Api;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.inject.Inject;
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
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.diegogusava.togus.endpoint.converter.UserConverter.*;

@Api(value = "Users")
@Path("/{apiversion : v1|v2}/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserEndpoint implements Serializable {

    @Inject
    UserRepository userRepository;

    @PathParam("apiversion")
    private String apiVersion;

    @GET
    @Path("/")
    public Response findAll() {
        List<UserDTO> dtos = userRepository.findAll().stream()
                .map(u -> of(u))
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{id}")
    @Cache(maxAge = 60 * 10)
    public Response findById(@PathParam("id") Integer id) {
        return userRepository.findById(id).map(
                user -> Response.ok(of(user)).build()
        ).orElse(
                Response.status(Response.Status.NOT_FOUND).build()
        );
    }

    @GET
    @Path("/{id}")
    @Cache(maxAge = 60 * 10)
    @Produces("application/vdn.diegogusava.user.complete+json")
    public Response findByIdComplete(@PathParam("id") Integer id) {
        return userRepository.findByIdComplete(id).map(
                tuple2 -> Response.ok(ofComplete(tuple2._1, tuple2._2)).build()
        ).orElse(
                Response.status(Response.Status.NOT_FOUND).build()
        );
    }


    @POST
    @Path("/")
    public Response add(@Context UriInfo uriInfo, UserDTO dto) {
        final User userPersisted = userRepository.persist(toUser(dto));
        URI result = uriInfo.getRequestUriBuilder()
                .path("{id}")
                .resolveTemplate("id", userPersisted.getId())
                .build();
        return Response.created(result).entity(of(userPersisted)).build();
    }

    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") Integer id, UserDTO dto) {
        userRepository.update(id, toUser(dto));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        if (userRepository.delete(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

}
