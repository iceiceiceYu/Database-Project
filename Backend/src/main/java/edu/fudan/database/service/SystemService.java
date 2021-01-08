package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Section;
import edu.fudan.database.domain.Ward;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService {
    private static final int MILD_NURSE_CAPACITY = 3;
    private static final int SEVERE_NURSE_CAPACITY = 2;
    private static final int CRITICAL_NURSE_CAPACITY = 1;
    private static final int MILD_WARD_CAPACITY = 4;
    private static final int SEVERE_WARD_CAPACITY = 2;
    private static final int CRITICAL_WARD_CAPACITY = 1;

    private static DailyInfoRepository dailyInfoRepository;
    private static PatientRepository patientRepository;
    private static ReportRepository reportRepository;
    private static SectionRepository sectionRepository;
    private static StaffRepository staffRepository;
    private static WardRepository wardRepository;

    @Autowired
    public SystemService(DailyInfoRepository dailyInfoRepository,
                         PatientRepository patientRepository,
                         ReportRepository reportRepository,
                         SectionRepository sectionRepository,
                         StaffRepository staffRepository,
                         WardRepository wardRepository) {
        SystemService.dailyInfoRepository = dailyInfoRepository;
        SystemService.patientRepository = patientRepository;
        SystemService.reportRepository = reportRepository;
        SystemService.sectionRepository = sectionRepository;
        SystemService.staffRepository = staffRepository;
        SystemService.wardRepository = wardRepository;
    }

    public static String arrangePatient(Patient patient) {
        String level = patient.getLevel();
        if (canArrange(level)) {
            int nurseCapacity, wardCapacity;
            if (level.equals("mild")) {
                nurseCapacity = MILD_NURSE_CAPACITY;
                wardCapacity = MILD_WARD_CAPACITY;
            } else if (level.equals("severe")) {
                nurseCapacity = SEVERE_NURSE_CAPACITY;
                wardCapacity = SEVERE_WARD_CAPACITY;
            } else {
                nurseCapacity = CRITICAL_NURSE_CAPACITY;
                wardCapacity = CRITICAL_WARD_CAPACITY;
            }

            patient.setQuarantined(false);
            patient.setSection(level);
            patient.setStatus(0);

            Section section = sectionRepository.findSectionByLevel(level);
            List<String> wardNurses = section.getWardNurses();
            List<String> wards = section.getWards();
            List<Patient> patients = (List<Patient>) patientRepository.findPatientBySectionAndStatus(level, 0);

            for (String wardNurse : wardNurses) {
                int cnt = 0;
                for (Patient currPatient : patients) {
                    if (currPatient.getWardNurse().equals(wardNurse)) {
                        cnt++;
                    }
                }
                if (cnt < nurseCapacity) {
                    patient.setWardNurse(wardNurse);
                    break;
                }
            }

            Loop:
            for (String wardName : wards) {
                int cnt = 0;
                for (Patient currPatient : patients) {
                    if (currPatient.getWardName().equals(wardName)) {
                        cnt++;
                    }
                }
                if (cnt < wardCapacity) {
                    patient.setWardName(wardName);
                    Ward ward = wardRepository.findWardByName(wardName);
                    List<String> currPatients = ward.getPatients();
                    List<Integer> sickBeds = ward.getSickbeds();

                    for (int i = 1; i <= wardCapacity; i++) {
                        if (!sickBeds.contains(i)) {
                            currPatients.add(patient.getName());
                            sickBeds.add(i);
                            ward.setPatients(currPatients);
                            ward.setSickbeds(sickBeds);
                            wardRepository.save(ward);
                            patient.setSickbed(i);
                            break Loop;
                        }
                    }
                }
            }
            patientRepository.save(patient);
            return "success";
        } else {
            patient.setQuarantined(true);
            patientRepository.save(patient);
            return "error";
        }
    }

    public static boolean canArrange(String level) {
        Section section = sectionRepository.findSectionByLevel(level);
        List<String> wardNurses = section.getWardNurses();
        List<String> wards = section.getWards();
        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySectionAndStatus(level, 0);

        if (level.equals("mild") && patients.size() < wardNurses.size() * 3) { // 判断病房护士是否足够
            return patients.size() < wards.size() * 4; // 判断床位是否足够
        } else if (level.equals("severe") && patients.size() < wardNurses.size() * 2) { // 判断病房护士是否足够
            return patients.size() < wards.size() * 2; // 判断床位是否足够
        } else if (level.equals("critical") && patients.size() < wardNurses.size()) { // 判断病房护士是否足够
            return patients.size() < wards.size(); // 判断床位是否足够
        }
        return false;
    }

    public static void transferPatient(Patient patient, boolean cascade) {
        String name = patient.getName();
        int sickbed = patient.getSickbed();
        String sectionName = patient.getSection();

        Section section = sectionRepository.findSectionByLevel(sectionName);
        List<String> wards = section.getWards();

        for (String wardName : wards) {
            Ward ward = wardRepository.findWardByName(wardName);
            List<String> patients = ward.getPatients();
            List<Integer> sickbeds = ward.getSickbeds();

            if (patients.contains(name) && sickbeds.contains(sickbed)) {
                patients.remove(name);
                sickbeds.remove((Integer) sickbed);
                ward.setPatients(patients);
                ward.setSickbeds(sickbeds);
                wardRepository.save(ward);
                break;
            }
        }

        patient.setSickbed(0);
        patientRepository.save(patient);

        if (cascade) {
            List<Patient> quarantinedPatients = (List<Patient>) patientRepository.findPatientByLevelAndQuarantined(sectionName, true);
            if (quarantinedPatients.size() > 0) {
                SystemService.arrangePatient(quarantinedPatients.get(0));
            } else {
                List<Patient> canTransPatients = SystemService.canTransPatient(sectionName);
                if (canTransPatients.size() > 0) {
                    SystemService.transferPatient(canTransPatients.get(0), true);
                }
            }
        }

        SystemService.arrangePatient(patient);
    }

    public static List<Patient> canTransPatient(String section) {
        List<Patient> patients = (List<Patient>) patientRepository.findPatientByLevelAndStatus(section, 0);
        List<Patient> selectedPatients = new ArrayList<>();

        for (Patient patient : patients) {
            if (!patient.getLevel().equals(patient.getSection())) {
                selectedPatients.add(patient);
            }
        }
        return selectedPatients;
    }
}
