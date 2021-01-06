package edu.fudan.database.service;

import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WardNurseService {
    private PatientRepository patientRepository;
    private ReportRepository reportRepository;
    private SectionRepository sectionRepository;
    private StaffRepository staffRepository;
    private WardRepository wardRepository;

    @Autowired
    public WardNurseService(PatientRepository patientRepository,
                         ReportRepository reportRepository,
                         SectionRepository sectionRepository,
                         StaffRepository staffRepository,
                         WardRepository wardRepository) {
        this.patientRepository = patientRepository;
        this.reportRepository = reportRepository;
        this.sectionRepository = sectionRepository;
        this.staffRepository = staffRepository;
        this.wardRepository = wardRepository;
    }
}
