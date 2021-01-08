package edu.fudan.database.repository;

import edu.fudan.database.domain.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    Iterable<Patient> findPatientBySection(String section);

    Iterable<Patient> findPatientBySectionAndStatus(String section, int status);

    Iterable<Patient> findPatientByLevel(String level);

    Iterable<Patient> findPatientByQuarantined(boolean quarantined);

    Iterable<Patient> findPatientByStatus(int status);

    Patient findPatientById(Long id);
}
