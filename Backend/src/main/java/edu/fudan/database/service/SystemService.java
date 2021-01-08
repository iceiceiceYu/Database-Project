package edu.fudan.database.service;

import edu.fudan.database.domain.*;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private static MessageRepository messageRepository;
    private static PatientRepository patientRepository;
    private static ReportRepository reportRepository;
    private static SectionRepository sectionRepository;
    private static StaffRepository staffRepository;
    private static WardRepository wardRepository;

    @Autowired
    public SystemService(DailyInfoRepository dailyInfoRepository,
                         MessageRepository messageRepository,
                         PatientRepository patientRepository,
                         ReportRepository reportRepository,
                         SectionRepository sectionRepository,
                         StaffRepository staffRepository,
                         WardRepository wardRepository) {
        SystemService.dailyInfoRepository = dailyInfoRepository;
        SystemService.messageRepository = messageRepository;
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

            SystemService.newMessage(section.getChiefNurse(), patient.getId(), patient.getName(), 1);
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

    public static void newMessage(String staff, Long patientId, String patientName, int messageType) {
        Message message = new Message(staff, patientId, patientName, messageType);
        messageRepository.save(message);
    }

    public static boolean testDischarge(Patient patient) {
        if (patient.getLevel().equals("mild")) {
            Long id = patient.getId();

            // 检验核酸检测结果
            List<Report> reports = (List<Report>) reportRepository.findReportByPatientId(id);
            int reportSize = reports.size();
            if (reportSize < 2) {
                return false;
            }
            Report last = reports.get(reportSize - 1);
            Report nextToLast = reports.get(reportSize - 2);

            Test:
            if (last.isPositive() || nextToLast.isPositive()) {
                return false;
            } else { // 检验两次时间大于24小时
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date lastDate = sdf.parse(last.getDate());
                    Date nextToLastDate = sdf.parse(nextToLast.getDate());
                    double time = (double) lastDate.getTime() - nextToLastDate.getTime();
                    if (time / (1000 * 60 * 60) <= 24) {
                        for (int i = 3; i <= reportSize; i++) {
                            nextToLast = reports.get(reportSize - i);
                            if (nextToLast.isPositive()) {
                                return false;
                            } else {
                                nextToLastDate = sdf.parse(nextToLast.getDate());
                                time = (double) lastDate.getTime() - nextToLastDate.getTime();
                                if (time / (1000 * 60 * 60) > 24) {
                                    break Test;
                                }
                            }
                        }
                        return false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // 检验每日记录体温
            List<DailyInfo> dailyInfos = (List<DailyInfo>) dailyInfoRepository.findDailyInfoByPatientId(id);
            int infoSize = dailyInfos.size();
            if (infoSize < 3) {
                return false;
            }
            for (int i = 1; i <= 3; i++) {
                if (dailyInfos.get(infoSize - i).getTemperature() >= 37.3) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
