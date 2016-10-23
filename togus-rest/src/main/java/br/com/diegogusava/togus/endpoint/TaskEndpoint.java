package br.com.diegogusava.togus.endpoint;

import br.com.diegogusava.togus.domain.Task;
import br.com.diegogusava.togus.dto.TaskDTO;
import br.com.diegogusava.togus.repository.TaskRepository;
import io.swagger.annotations.Api;

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

import static br.com.diegogusava.togus.endpoint.converter.TaskConverter.of;
import static br.com.diegogusava.togus.endpoint.converter.TaskConverter.toTask;

@Api(value = "Task")
@Path("/{apiversion : v1|v2}/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskEndpoint implements Serializable {

    @Inject
    private TaskRepository repository;


    @GET
    public Response findAll() {
        return Response.ok(repository.findAll()).build();
    }

    @POST
    @Path("/")
    public Response add(@Context UriInfo uriInfo,
                        TaskDTO task) {
        final Task taskPersisted = repository.persist(toTask(task));
        URI result = uriInfo.getRequestUriBuilder()
                .path("{id}")
                .resolveTemplate("id", taskPersisted.getId())
                .build();
        return Response.created(result).entity(of(taskPersisted)).build();
    }

    @PUT
    @Path("/{taskId}")
    public Response put(@PathParam("taskId") Integer taskId,
                        TaskDTO taskDTO) {
        repository.update(taskId, toTask(taskDTO));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{taskId}")
    public Response delete(@PathParam("taskId") Integer taskId) {
        if (repository.delete(taskId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
