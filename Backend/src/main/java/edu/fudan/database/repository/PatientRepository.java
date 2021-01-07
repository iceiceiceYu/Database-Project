package edu.fudan.database.repository;

import edu.fudan.database.domain.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    Iterable<Patient> findPatientBySection(String section);

    Patient findPatientById(Long id);
}
