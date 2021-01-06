package edu.fudan.database.service;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    private PatientRepository patientRepository;
    private ReportRepository reportRepository;
    private SectionRepository sectionRepository;
    private StaffRepository staffRepository;
    private WardRepository wardRepository;

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
        List<Patient> patients;

        Staff doctor = staffRepository.findStaffByUsername(doctorUsername);
        String section = doctor.getSection();

        patients = (List<Patient>) patientRepository.findPatientBySection(section);
        System.out.println(patients);
        return patients;
    }
}
