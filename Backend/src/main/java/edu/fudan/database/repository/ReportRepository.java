package edu.fudan.database.repository;

import edu.fudan.database.domain.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {
    Iterable<Report> findReportByName(String name);

    Iterable<Report> findReportByDate(String date);

    Iterable<Report> findReportByNameAndDate(String name, String date);
}
