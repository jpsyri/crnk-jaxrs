package net.kirnu;

import net.kirnu.crnk.repositiories.TestResourceRepository;
import net.kirnu.filter.TestPostMatchingFilter;
import net.kirnu.filter.TestPreMatchingFilter;
import net.kirnu.resources.TestJaxRsResource;

import io.crnk.core.module.SimpleModule;
import io.crnk.rs.CrnkFeature;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TestApplication extends Application<TestApplicationConfig> {

    public static final String CRNK_PATH_PREFIX = "crnk";

    public void run(TestApplicationConfig testApplicationConfig, Environment environment) throws Exception {

        // Initialize Crnk
        CrnkFeature crnkFeature = new CrnkFeature();
        crnkFeature.getBoot().setWebPathPrefix(CRNK_PATH_PREFIX);
        SimpleModule repositories = new SimpleModule("repositories");
        repositories.addRepository(new TestResourceRepository());
        crnkFeature.addModule(repositories);
        environment.jersey().register(crnkFeature);

        // Add JAX-RS resource
        environment.jersey().register(new TestJaxRsResource());

        // Add pre- and postmatching filter
        environment.jersey().register(new TestPreMatchingFilter());
        environment.jersey().register(new TestPostMatchingFilter());
    }
}
