package edu.fudan.database.repository;

import edu.fudan.database.domain.Staff;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Long> {
}
