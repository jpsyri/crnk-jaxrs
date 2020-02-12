package net.kirnu;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import net.kirnu.filter.TestPostMatchingFilter;
import net.kirnu.crnk.resources.TestResource;

import io.crnk.client.CrnkClient;
import io.crnk.core.exception.ForbiddenException;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepository;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;


@ExtendWith(DropwizardExtensionsSupport.class)
public class TestApplicationTest {

    private static final DropwizardAppExtension<TestApplicationConfig> dropwizard
        = new DropwizardAppExtension<>(
            TestApplication.class,
        ResourceHelpers.resourceFilePath("test-app-config.yaml")
    );

    // These demonstrate that for normal JAX-RS requests the filter is applied and working
    @Test
    public void testJaxRs_unauthorized() {
        Client client = dropwizard.client();

        Response response = client.target(
            String.format("http://localhost:%d/jax", dropwizard.getLocalPort())
        ).request()
            .get();

        // Without auth header, test filter blocks the JAX-RS request
        Assertions.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void testJaxRs_authorized() {
        Client client = dropwizard.client();

        Response response = client.target(
            String.format("http://localhost:%d/jax", dropwizard.getLocalPort())
        ).request()
            .header(TestPostMatchingFilter.AUTH_HEADER, TestPostMatchingFilter.AUTH_HEADER_AUHTENTICATED_VALUE)
            .get();

        // With auth header, JAX-RS request passes through the test filter
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    // This demonstrates that Crnk requests ignore the filter, if filter was applied, this wouldn't pass
    @Test
    public void testCrnk_unauthorized() {
        CrnkClient client = new CrnkClient(
            String.format("http://localhost:%d/%s", dropwizard.getLocalPort(), TestApplication.CRNK_PATH_PREFIX)
        );

        // If filter was applied the .create() and .findOne() should result error (throw ForbiddenException)
        ResourceRepository<TestResource, Object> repository = client.getRepositoryForType(TestResource.class);
        TestResource resource = new TestResource(12L);
        repository.create(resource);

        TestResource result = repository.findOne(12L, new QuerySpec(TestResource.class));
        Assertions.assertEquals(resource.getId(), result.getId());
    }

    // If the filter was applied properly to Crnk requests, this test would pass
    @Test
    public void testCrnk_unauthorized2() {
        CrnkClient client = new CrnkClient(
            String.format("http://localhost:%d/%s", dropwizard.getLocalPort(), TestApplication.CRNK_PATH_PREFIX)
        );

        // If filter was applied the .create() and .findOne() should result error (throw ForbiddenException)
        ResourceRepository<TestResource, Object> repository = client.getRepositoryForType(TestResource.class);
        TestResource resource = new TestResource(12L);
        Assertions.assertThrows(
            ForbiddenException.class,
            () -> repository.create(resource)
        );


        Assertions.assertThrows(
            ForbiddenException.class,
            () -> repository.findOne(12L, new QuerySpec(TestResource.class))

        );
    }
}
