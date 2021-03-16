package com.test;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import io.smallrye.mutiny.Uni;

@Path("/increments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IncrementResource {
    @Inject
    IncrementService service;

    @GET
    public Uni<List<String>> keys() {
        return service.keys();
    }

    @POST
    public Increment create(Increment increment) {
        service.set(increment.type, increment.code, increment.value);
        return increment;
    }

    @GET
    @Path("/{type}/{code}")
    public Increment get(@PathParam("type") String type, @PathParam("code") String code) {
        return new Increment(type, code, service.get(type,code));
    }

    /*
    @PUT
    @Path("/{key}")
    public void increment(@PathParam("key") String key, Integer value) {
        service.increment(key, value);
    }*/

    @DELETE
    @Path("/{key}")
    public Uni<Void> delete(@PathParam("key") String key) {
        return service.del(key);
    }
}
