package net.kirnu.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class TestPostMatchingFilter implements ContainerRequestFilter {

    public static final String AUTH_HEADER = "auth";
    public static final String AUTH_HEADER_AUHTENTICATED_VALUE = "yes-authenticated";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        logger.log(Level.INFO, "TestPostMatchingFilter.filter() - starting auth check");
        String authValue = containerRequestContext.getHeaderString(AUTH_HEADER);
        if(!AUTH_HEADER_AUHTENTICATED_VALUE.equals(authValue)) {
            logger.log(Level.WARNING, "TestPostMatchingFilter.filter() - forbidden");
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            return;
        }
        logger.log(Level.INFO, "TestPostMatchingFilter.filter() - auth check OK, proceeding");
    }
}
