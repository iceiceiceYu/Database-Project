package edu.fudan.database.repository;

import edu.fudan.database.domain.Ward;
import org.springframework.data.repository.CrudRepository;

public interface WardRepository extends CrudRepository<Ward, Long> {
    Ward findWardByName(String name);
}
