package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Section;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ChiefNurseService {
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final SectionRepository sectionRepository;
    private final StaffRepository staffRepository;
    private final WardRepository wardRepository;

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
        // TODO
//        for (Patient patient : patients) {
//            if (patient.isAlive()) {
//                selectedPatients.add(patient);
//            }
//        }
        return selectedPatients;
    }

    public List<Staff> wardNurse(String chiefNurseUsername) {
        Staff chiefNurse = staffRepository.findStaffByUsername(chiefNurseUsername);
        String section = chiefNurse.getSection();

        return (List<Staff>) staffRepository.findStaffBySectionAndType(section, "ward nurse");
    }

    public List<String> patientsOfNurse(String chiefNurseUsername) {
        Staff chiefNurse = staffRepository.findStaffByUsername(chiefNurseUsername);
        String section = chiefNurse.getSection();

        List<Staff> staff = (List<Staff>) staffRepository.findStaffBySectionAndType(section, "ward nurse");
        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySection(section);

        List<String> patientsOfNurse = new ArrayList<>();
        for (Staff wardNurse : staff) {
            patientsOfNurse.add(searchPatient(wardNurse, patients));
        }
        return patientsOfNurse;
    }

    private String searchPatient(Staff staff, List<Patient> patients) {
        StringBuilder sb = new StringBuilder();
        String wardNurse = staff.getUsername();
        for (Patient patient : patients) {
            if (patient.getWardNurse().equals(wardNurse)) {
                sb.append(patient.getName()).append(" ");
            }
        }
        return sb.toString();
    }

    public Staff newNurse(String chiefNurseUsername, String wardNurseUsername) {
        Staff chiefNurse = staffRepository.findStaffByUsername(chiefNurseUsername);
        String sectionName = chiefNurse.getSection();
        Staff staff = staffRepository.findStaffByUsername(wardNurseUsername);
        staff.setSection(sectionName);
        staffRepository.save(staff);

        Section section = sectionRepository.findSectionByChiefNurse(chiefNurseUsername);
        Set<String> wardNurses = section.getWardNurses();
        wardNurses.add(wardNurseUsername);
        section.setWardNurses(wardNurses);
        sectionRepository.save(section);

        // TODO: 安排病人
        return staff;
    }

    public String deleteNurse(String chiefNurseUsername, String wardNurseUsername) {
        Staff chiefNurse = staffRepository.findStaffByUsername(chiefNurseUsername);
        String sectionName = chiefNurse.getSection();
        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySectionAndStatus(sectionName, 0);

        Section section = sectionRepository.findSectionByChiefNurse(chiefNurseUsername);
        Set<String> wardNurses = section.getWardNurses();

        if (sectionName.equals("mild")) {
            if ((wardNurses.size() - 1) * 3 < patients.size()) {
                return "fail";
            }
        } else if (sectionName.equals("severe")) {
            if ((wardNurses.size() - 1) * 2 < patients.size()) {
                return "fail";
            }
        } else {
            if ((wardNurses.size() - 1) < patients.size()) {
                return "fail";
            }
        }

        wardNurses.remove(wardNurseUsername);
        section.setWardNurses(wardNurses);
        sectionRepository.save(section);

        Staff staff = staffRepository.findStaffByUsername(wardNurseUsername);
        staff.setSection("backup");
        staffRepository.save(staff);

        // TODO: 安排病人
        return "success";
    }

    public List<Staff> searchBackupWard(String chiefNurseUsername) {
        return (List<Staff>) staffRepository.findStaffBySectionAndType("backup", "ward nurse");
    }
}
