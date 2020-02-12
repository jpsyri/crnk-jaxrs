package net.kirnu.crnk.repositiories;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.kirnu.crnk.resources.TestResource;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepository;
import io.crnk.core.resource.list.ResourceList;

/*
 * Just simple placeholder repository that logs requests
 */
public class TestResourceRepository implements ResourceRepository<TestResource, Long> {

    private Map<Long, TestResource> data = new HashMap<>();
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public Class<TestResource> getResourceClass() {
        return TestResource.class;
    }

    public TestResource findOne(Long id, QuerySpec querySpec) {
        logger.info("TestResourceRepository.findOne()");
        return data.get(id);
    }

    public ResourceList<TestResource> findAll(QuerySpec querySpec) {
        logger.info("TestResourceRepository.findAll()");
        return querySpec.apply(Collections.emptyList());
    }

    public ResourceList<TestResource> findAll(Collection<Long> collection, QuerySpec querySpec) {
        logger.info("TestResourceRepository.findAll()");
        return querySpec.apply(Collections.emptyList());
    }

    public <S extends TestResource> S save(S s) {
        logger.info("TestResourceRepository.save()");
        data.put(s.getId(), s);
        return s;
    }

    public <S extends TestResource> S create(S s) {
        logger.info("TestResourceRepository.create()");
        data.put(s.getId(), s);
        return s;
    }

    public void delete(Long id) {
        logger.info("TestResourceRepository.delete()");
        data.remove(id);
    }
}
