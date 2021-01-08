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
public class WardNurseService {
    private final DailyInfoRepository dailyInfoRepository;
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final SectionRepository sectionRepository;
    private final StaffRepository staffRepository;
    private final WardRepository wardRepository;

    @Autowired
    public WardNurseService(DailyInfoRepository dailyInfoRepository,
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

    public List<Patient> getPatientInfo(String wardNurseUsername) {
        Staff wardNurse = staffRepository.findStaffByUsername(wardNurseUsername);
        String section = wardNurse.getSection();

        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySectionAndStatus(section,0);
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
            if (testDischarge(patient)) {
                selectedPatients.add(patient);
            }
        }
        return selectedPatients;
    }

    private boolean testDischarge(Patient patient) {
        if (patient.getLevel().equals("mild")) {
            Long id = patient.getId();

            // 检验核酸检测结果
            List<Report> reports = (List<Report>) reportRepository.findReportByPatientId(id);
            int reportSize = reports.size();
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
            if (patient.getStatus() == 0 || patient.getStatus() == 1) {
                selectedPatients.add(patient);
            }
        }
        return selectedPatients;
    }

    public DailyInfo dailyInfo(Long patientId, String patientName, double temperature, String symptom, boolean positive, String date, String wardNurse) {
        DailyInfo dailyInfo = new DailyInfo(patientId, patientName, temperature, symptom, positive, date.substring(0, 10), wardNurse);
        dailyInfoRepository.save(dailyInfo);
        if (SystemService.testDischarge(patientRepository.findPatientById(patientId))) {
            String sectionName = staffRepository.findStaffByUsername(wardNurse).getSection();
            Section section = sectionRepository.findSectionByLevel(sectionName);
            SystemService.newMessage(section.getDoctor(), patientId, patientName, 2);
        }
        return dailyInfo;
    }
}
