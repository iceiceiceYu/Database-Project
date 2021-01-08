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
public class ChiefNurseService {
    private final DailyInfoRepository dailyInfoRepository;
    private final MessageRepository messageRepository;
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final SectionRepository sectionRepository;
    private final StaffRepository staffRepository;
    private final WardRepository wardRepository;

    @Autowired
    public ChiefNurseService(DailyInfoRepository dailyInfoRepository,
                             MessageRepository messageRepository,
                             PatientRepository patientRepository,
                             ReportRepository reportRepository,
                             SectionRepository sectionRepository,
                             StaffRepository staffRepository,
                             WardRepository wardRepository) {
        this.dailyInfoRepository = dailyInfoRepository;
        this.messageRepository = messageRepository;
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
        List<String> wardNurses = section.getWardNurses();
        wardNurses.add(wardNurseUsername);
        section.setWardNurses(wardNurses);
        sectionRepository.save(section);

        List<Patient> quarantinedPatients = (List<Patient>) patientRepository.findPatientByLevelAndQuarantined(sectionName, true);
        List<Patient> canTransPatients = SystemService.canTransPatient(sectionName);
        while (SystemService.canArrange(sectionName) && (quarantinedPatients.size() > 0 || canTransPatients.size() > 0)) {
            if (quarantinedPatients.size() > 0) {
                SystemService.arrangePatient(quarantinedPatients.get(0));
                quarantinedPatients.remove(0);
            } else {
                SystemService.transferPatient(canTransPatients.get(0), true);
                canTransPatients.remove(0);
            }
        }
        return staff;
    }

    public String deleteNurse(String chiefNurseUsername, String wardNurseUsername) {
        Staff chiefNurse = staffRepository.findStaffByUsername(chiefNurseUsername);
        String sectionName = chiefNurse.getSection();
        List<Patient> patients = (List<Patient>) patientRepository.findPatientBySectionAndStatus(sectionName, 0);

        Section section = sectionRepository.findSectionByChiefNurse(chiefNurseUsername);
        List<String> wardNurses = section.getWardNurses();

        if (sectionName.equals("mild") && (wardNurses.size() - 1) * 3 < patients.size()) {
            return "fail";
        } else if (sectionName.equals("severe") && (wardNurses.size() - 1) * 2 < patients.size()) {
            return "fail";
        } else if (sectionName.equals("critical") && (wardNurses.size() - 1) < patients.size()) {
            return "fail";
        }

        wardNurses.remove(wardNurseUsername);
        section.setWardNurses(wardNurses);
        sectionRepository.save(section);

        Staff staff = staffRepository.findStaffByUsername(wardNurseUsername);
        staff.setSection("backup");
        staffRepository.save(staff);

        for (Patient patient : patients) {
            if (patient.getWardNurse().equals(wardNurseUsername)) {
                SystemService.transferPatient(patient, false);
            }
        }
        return "success";
    }

    public List<Staff> searchBackupWard(String chiefNurseUsername) {
        return (List<Staff>) staffRepository.findStaffBySectionAndType("backup", "ward nurse");
    }

    public List<String> wardInfo(String chiefNurseUsername) {
        List<String> wardInfo = new ArrayList<>();
        Section section = sectionRepository.findSectionByChiefNurse(chiefNurseUsername);
        List<String> wards = section.getWards();
        int wardCapacity = getWardCapacity(section.getLevel());

        for (String ward : wards) {
            for (int i = 1; i <= wardCapacity; i++) {
                wardInfo.add(ward);
            }
        }
        return wardInfo;
    }

    public List<String> sickbedInfo(String chiefNurseUsername) {
        List<String> sickbedInfo = new ArrayList<>();
        Section section = sectionRepository.findSectionByChiefNurse(chiefNurseUsername);
        List<String> wards = section.getWards();
        int wardCapacity = getWardCapacity(section.getLevel());

        for (String ward : wards) {
            for (int i = 1; i <= wardCapacity; i++) {
                sickbedInfo.add("病床" + i);
            }
        }
        return sickbedInfo;
    }

    public List<String> patientInfo(String chiefNurseUsername) {
        List<String> patientInfo = new ArrayList<>();
        Section section = sectionRepository.findSectionByChiefNurse(chiefNurseUsername);
        List<String> wards = section.getWards();
        int wardCapacity = getWardCapacity(section.getLevel());

        for (String wardName : wards) {
            Ward ward = wardRepository.findWardByName(wardName);
            List<String> patients = ward.getPatients();
            List<Integer> sickbeds = ward.getSickbeds();
            for (int i = 1; i <= wardCapacity; i++) {
                if (sickbeds.contains(i)) {
                    patientInfo.add(patients.get(sickbeds.indexOf(i)));
                } else {
                    patientInfo.add("空置");
                }
            }
        }
        return patientInfo;
    }

    private int getWardCapacity(String level) {
        if (level.equals("mild")) {
            return 4;
        } else if (level.equals("severe")) {
            return 2;
        } else {
            return 1;
        }
    }

    public List<String> getMessage(String chiefNurseUsername) {
        List<Message> messageList = (List<Message>) messageRepository.findMessageByStaffAndMessageType(chiefNurseUsername, 1);
        List<String> messages = new ArrayList<>();

        for (Message message : messageList) {
            messages.add(message.getStaff() + " 护士长：病人 " + message.getPatientName() + " 已经转入您的治疗区域了");
        }
        return messages;
    }
}
