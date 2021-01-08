package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyNurseService {
    private final DailyInfoRepository dailyInfoRepository;
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final SectionRepository sectionRepository;
    private final StaffRepository staffRepository;
    private final WardRepository wardRepository;

    @Autowired
    public EmergencyNurseService(DailyInfoRepository dailyInfoRepository,
                                 PatientRepository patientRepository,
                                 ReportRepository reportRepository,
                                 SectionRepository sectionRepository,
                                 StaffRepository staffRepository,
                                 WardRepository wardRepository) {
        this.dailyInfoRepository = dailyInfoRepository;
        this.patientRepository = patientRepository;
        this.reportRepository = reportRepository;
        this.sectionRepository = sectionRepository;
        this.staffRepository = staffRepository;
        this.wardRepository = wardRepository;
    }

    public Patient newPatient(String name, String gender, int age, String level) {
        Patient patient = new Patient(name, gender, age, level);
        return SystemService.arrangePatient(patient);
    }

    public List<Patient> getPatientInfo(String emergencyNurseUsername) {
        return (List<Patient>) patientRepository.findPatientByStatus(0);
    }

    public List<Patient> select(String emergencyNurseUsername, String type, String value) {
        List<Patient> selectedPatients;
        switch (type) {
            case "section":
                selectedPatients = whichSection(value);
                break;
            case "level":
                selectedPatients = whichLevel(value);
                break;
            case "isWaiting":
                selectedPatients = isQuarantined(value);
                break;
            case "live":
                selectedPatients = isAlive(value);
                break;
            default:
                return (List<Patient>) patientRepository.findPatientByStatus(0);
        }
        return selectedPatients;
    }

    private List<Patient> whichSection(String section) {
        return (List<Patient>) patientRepository.findPatientBySection(section);
    }

    private List<Patient> whichLevel(String level) {
        return (List<Patient>) patientRepository.findPatientByLevel(level);
    }

    private List<Patient> isQuarantined(String value) {
        if (value.equals("true")) {
            return (List<Patient>) patientRepository.findPatientByQuarantined(true);
        } else {
            return (List<Patient>) patientRepository.findPatientByQuarantined(false);
        }
    }

    private List<Patient> isAlive(String value) {
        if (value.equals("true")) {
            List<Patient> patients = (List<Patient>) patientRepository.findPatientByStatus(0);
            patients.addAll((List<Patient>) patientRepository.findPatientByStatus(1));
            return patients;
        } else {
            return (List<Patient>) patientRepository.findPatientByStatus(-1);
        }
    }
}
