package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Section;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SystemService {
    private static PatientRepository patientRepository;
    private static ReportRepository reportRepository;
    private static SectionRepository sectionRepository;
    private static StaffRepository staffRepository;
    private static WardRepository wardRepository;

    @Autowired
    public SystemService(PatientRepository patientRepository,
                         ReportRepository reportRepository,
                         SectionRepository sectionRepository,
                         StaffRepository staffRepository,
                         WardRepository wardRepository) {
        SystemService.patientRepository = patientRepository;
        SystemService.reportRepository = reportRepository;
        SystemService.sectionRepository = sectionRepository;
        SystemService.staffRepository = staffRepository;
        SystemService.wardRepository = wardRepository;
    }

    public static Patient arrangePatient(Patient patient) {
        String level = patient.getLevel();
        if (canArrange(level)) {

        } else {
            patient.setQuarantined(true);
            patientRepository.save(patient);
        }
        return patient;
    }

    private static boolean canArrange(String level) {
        Section section = sectionRepository.findSectionByLevel(level);
        String doctor = section.getDoctor();
        Set<String> wardNurses = section.getWardNurses();

        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySectionAndStatus(level, 0);

        if (level.equals("mild") && patients.size() < wardNurses.size() * 3) {
            return true;
        } else if (level.equals("severe") && patients.size() < wardNurses.size() * 2) {
            return true;
        } else if (level.equals("critical") && patients.size() < wardNurses.size()) {
            return true;
        }

        return false;
    }
}
