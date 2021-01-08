package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Section;
import edu.fudan.database.domain.Ward;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public static Patient arrangePatient(Patient patient) {
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
            Set<String> wardNurses = section.getWardNurses();
            Set<String> wards = section.getWards();
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
                    Set<String> currPatients = ward.getPatients();
                    Set<Integer> sickBeds = ward.getSickbeds();

                    for (int i = 1; i <= wardCapacity; i++) {
                        if (!sickBeds.contains(i)) {
                            sickBeds.add(i);
                            currPatients.add(patient.getName());
                            patient.setSickbed(i);
                            break Loop;
                        }
                    }
                }
            }
            patientRepository.save(patient);
        } else {
            patient.setQuarantined(true);
            patientRepository.save(patient);
        }
        return patient;
    }

    private static boolean canArrange(String level) {
        Section section = sectionRepository.findSectionByLevel(level);
        Set<String> wardNurses = section.getWardNurses();
        Set<String> wards = section.getWards();
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
}
