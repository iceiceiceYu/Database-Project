package edu.fudan.database.repository;

import edu.fudan.database.domain.Section;
import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends CrudRepository<Section, Long> {
    Section findSectionByLevel(String level);

    Section findSectionByDoctor(String doctor);

    Section findSectionByChiefNurse(String chiefNurse);
}
