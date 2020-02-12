package net.kirnu.crnk.resources;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;

@JsonApiResource(type = "test-resource")
public class TestResource {
    @JsonApiId
    Long id;

    public TestResource() {
    }

    public TestResource(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
