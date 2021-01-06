package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChiefNurseService {
    private PatientRepository patientRepository;
    private ReportRepository reportRepository;
    private SectionRepository sectionRepository;
    private StaffRepository staffRepository;
    private WardRepository wardRepository;

    @Autowired
    public ChiefNurseService(PatientRepository patientRepository,
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

    public List<Patient> getPatientInfo(String chiefNurseUsername) {
        Staff chiefNurse = staffRepository.findStaffByUsername(chiefNurseUsername);
        String section = chiefNurse.getSection();

        return (List<Patient>) patientRepository.findPatientBySection(section);
    }

    public List<Patient> select(String chiefNurseUsername, String type, String value) {
        Staff chiefNurse = staffRepository.findStaffByUsername(chiefNurseUsername);
        String section = chiefNurse.getSection();

        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySection(section);
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
        for (Patient patient : patients) {
            if (patient.isAlive()) {
                selectedPatients.add(patient);
            }
        }
        return selectedPatients;
    }

    public List<Staff> wardNurse(String chiefNurseUsername) {
        Staff chiefNurse = staffRepository.findStaffByUsername(chiefNurseUsername);
        String section = chiefNurse.getSection();

        return (List<Staff>) staffRepository.findStaffBySectionAndType(section,"ward nurse");
    }
}
