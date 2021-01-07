package edu.fudan.database.repository;

import edu.fudan.database.domain.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {
    Iterable<Report> findReportByPatientName(String patientName);

    Iterable<Report> findReportByDate(String date);

    Iterable<Report> findReportByPatientNameAndDate(String patientName, String date);
}
