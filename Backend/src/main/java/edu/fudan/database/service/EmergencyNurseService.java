package edu.fudan.database.service;

import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmergencyNurseService {
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final SectionRepository sectionRepository;
    private final StaffRepository staffRepository;
    private final WardRepository wardRepository;

    @Autowired
    public EmergencyNurseService(PatientRepository patientRepository,
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
