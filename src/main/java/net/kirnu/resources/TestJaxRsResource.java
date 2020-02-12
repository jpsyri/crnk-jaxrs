package net.kirnu.resources;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/jax")
public class TestJaxRsResource {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @GET
    public Response get() {
        logger.info("TestJaxRsResource.get()");
        return Response.status(Response.Status.OK).build();
    }
}
