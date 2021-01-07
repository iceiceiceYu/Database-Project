package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WardNurseService {
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final SectionRepository sectionRepository;
    private final StaffRepository staffRepository;
    private final WardRepository wardRepository;

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

    public List<Patient> getPatientInfo(String wardNurseUsername) {
        Staff wardNurse = staffRepository.findStaffByUsername(wardNurseUsername);
        String section = wardNurse.getSection();

        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySection(section);
        patients.removeIf(patient -> !patient.getWardNurse().equals(wardNurseUsername));
        return patients;
    }

    public List<Patient> select(String wardNurseUsername, String type, String value) {
        Staff wardNurse = staffRepository.findStaffByUsername(wardNurseUsername);
        String section = wardNurse.getSection();

        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySection(section);
        patients.removeIf(patient -> !patient.getWardNurse().equals(wardNurseUsername));
        List<Patient> selectedPatients;
        switch (type) {
            case "discharge":
                selectedPatients = canDischarge(patients);
                break;
            case "trans":
                selectedPatients = canTrans(patients);
                break;
            case "live":
                selectedPatients = isAlive(patients);
                break;
            default:
                return patients;
        }
        if (value.equals("true")) {
            return selectedPatients;
        } else {
            patients.removeAll(selectedPatients);
            return patients;
        }
    }

    private List<Patient> canDischarge(List<Patient> patients) {
        List<Patient> selectedPatients = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getLevel().equals("mild")) {
                // TODO
            }
        }
        return selectedPatients;
    }

    private List<Patient> canTrans(List<Patient> patients) {
        List<Patient> selectedPatients = new ArrayList<>();
        for (Patient patient : patients) {
            if (!patient.getLevel().equals(patient.getSection())) {
                selectedPatients.add(patient);
            }
        }
        return selectedPatients;
    }

    private List<Patient> isAlive(List<Patient> patients) {
        List<Patient> selectedPatients = new ArrayList<>();
        // TODO
//        for (Patient patient : patients) {
//            if (patient.isAlive()) {
//                selectedPatients.add(patient);
//            }
//        }
        return selectedPatients;
    }
}
