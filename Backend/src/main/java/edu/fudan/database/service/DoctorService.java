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
public class DoctorService {
    private final DailyInfoRepository dailyInfoRepository;
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final SectionRepository sectionRepository;
    private final StaffRepository staffRepository;
    private final WardRepository wardRepository;

    @Autowired
    public DoctorService(DailyInfoRepository dailyInfoRepository,
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

    public List<Staff> chiefNurse(String doctorUsername) {
        Staff doctor = staffRepository.findStaffByUsername(doctorUsername);
        String section = doctor.getSection();

        return (List<Staff>) staffRepository.findStaffBySectionAndType(section, "chief nurse");
    }

    public List<Staff> wardNurse(String doctorUsername) {
        Staff doctor = staffRepository.findStaffByUsername(doctorUsername);
        String section = doctor.getSection();

        return (List<Staff>) staffRepository.findStaffBySectionAndType(section, "ward nurse");
    }

    public List<String> patientsOfNurse(String doctorUsername) {
        Staff doctor = staffRepository.findStaffByUsername(doctorUsername);
        String section = doctor.getSection();

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

    public Patient modifyLevel(Long patientId, String newLevel) {
        Patient patient = patientRepository.findPatientById(patientId);
        patient.setLevel(newLevel);
        patientRepository.save(patient);

        if (SystemService.canArrange(newLevel)) {
            SystemService.transferPatient(patient, true);
        }
        return patient;
    }

    public Patient modifyAlive(Long patientId) {
        Patient patient = patientRepository.findPatientById(patientId);
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

        patient.setStatus(-1);
        patient.setSickbed(0);
        patientRepository.save(patient);

        List<Patient> quarantinedPatients = (List<Patient>) patientRepository.findPatientByLevelAndQuarantined(sectionName, true);
        if (quarantinedPatients.size() > 0) {
            SystemService.arrangePatient(quarantinedPatients.get(0));
        } else {
            List<Patient> canTransPatients = SystemService.canTransPatient(sectionName);
            if (canTransPatients.size() > 0) {
                SystemService.transferPatient(canTransPatients.get(0), true);
            }
        }
        return patient;
    }

    public Report addReport(Long patientId, String patientName, boolean positive, String level, String date, String doctor) {
        Report report = new Report(patientId, patientName, positive, level, date, doctor);
        reportRepository.save(report);
        return report;
    }

    public Patient discharge(Long patientId) {
        Patient patient = patientRepository.findPatientById(patientId);
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

        patient.setStatus(1);
        patient.setSickbed(0);
        patientRepository.save(patient);

        List<Patient> quarantinedPatients = (List<Patient>) patientRepository.findPatientByLevelAndQuarantined(sectionName, true);
        if (quarantinedPatients.size() > 0) {
            SystemService.arrangePatient(quarantinedPatients.get(0));
        } else {
            List<Patient> canTransPatients = SystemService.canTransPatient(sectionName);
            if (canTransPatients.size() > 0) {
                SystemService.transferPatient(canTransPatients.get(0), true);
            }
        }
        return patient;
    }
}
