package net.kirnu.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION)
@PreMatching
public class TestPreMatchingFilter implements ContainerRequestFilter {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        // Just filter with logging to show that prematching filters get applied
        logger.info("TestPreMatchingFilter.filter()");
    }
}
