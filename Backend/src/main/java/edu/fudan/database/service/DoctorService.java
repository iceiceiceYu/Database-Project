package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Report;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final SectionRepository sectionRepository;
    private final StaffRepository staffRepository;
    private final WardRepository wardRepository;

    @Autowired
    public DoctorService(PatientRepository patientRepository,
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

    public List<Patient> getPatientInfo(String doctorUsername) {
        Staff doctor = staffRepository.findStaffByUsername(doctorUsername);
        String section = doctor.getSection();

        return (List<Patient>) patientRepository.findPatientBySection(section);
    }

    public List<Patient> select(String doctorUsername, String type, String value) {
        Staff doctor = staffRepository.findStaffByUsername(doctorUsername);
        String section = doctor.getSection();

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

    public List<Staff> chiefNurse(String doctorUsername) {
        Staff doctor = staffRepository.findStaffByUsername(doctorUsername);
        String section = doctor.getSection();

        return (List<Staff>) staffRepository.findStaffBySectionAndType(section, "chief nurse");
    }

    public Map<Staff, String> wardNurse(String doctorUsername) {
        Staff doctor = staffRepository.findStaffByUsername(doctorUsername);
        String section = doctor.getSection();

        List<Staff> staff = (List<Staff>) staffRepository.findStaffBySectionAndType(section, "ward nurse");
        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySection(section);

        Map<Staff, String> staffPatientMap = new HashMap<>();
        for (Staff wardNurse : staff) {
            staffPatientMap.put(wardNurse, searchPatient(wardNurse, patients));
        }
        System.out.println(staffPatientMap);
        return staffPatientMap;
    }

    private String searchPatient(Staff staff, List<Patient> patients) {
        StringBuilder sb = new StringBuilder();
        String wardNurse = staff.getUsername();
        for (Patient patient : patients) {
            if (patient.getWardNurse().equals(wardNurse)){
                sb.append(patient.getName()).append(" ");
            }
        }
        return sb.toString();
    }

    public Patient modifyLevel(Long patientId, String newLevel) {
        Patient patient = patientRepository.findPatientById(patientId);
        patient.setLevel(newLevel);
        patientRepository.save(patient);
        return patient;
    }

    public Patient modifyAlive(Long patientId) {
        Patient patient = patientRepository.findPatientById(patientId);
        patient.setStatus(-1);
        patientRepository.save(patient);
        return patient;
    }

    public Report addReport(Long patientId, String patientName, boolean positive, String level, String date, String doctor) {
        Report report = new Report(patientId, patientName, positive, level, date, doctor);
        reportRepository.save(report);
        return report;
    }

    public Patient discharge(Long patientId) {
        Patient patient = patientRepository.findPatientById(patientId);
        patient.setStatus(1);
        patientRepository.save(patient);
        return patient;
    }
}
