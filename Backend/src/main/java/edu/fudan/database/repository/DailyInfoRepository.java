package edu.fudan.database.repository;

import edu.fudan.database.domain.DailyInfo;
import org.springframework.data.repository.CrudRepository;

public interface DailyInfoRepository extends CrudRepository<DailyInfo, Long> {
}
