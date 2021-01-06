package edu.fudan.database;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Section;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DatabaseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseProjectApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(PatientRepository patientRepository,
                                        ReportRepository reportRepository,
                                        SectionRepository sectionRepository,
                                        StaffRepository staffRepository,
                                        WardRepository wardRepository) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                SectionLoader(sectionRepository);
                StaffLoader(staffRepository);
                WardLoader(wardRepository);
                PatientLoader(patientRepository);
            }

            private void SectionLoader(SectionRepository sectionRepository) {
                Set<String> mWard = new HashSet<>();
                mWard.add("mWard1");
                mWard.add("mWard2");
                mWard.add("mWard3");
                mWard.add("mWard4");
                Set<String> sWard = new HashSet<>();
                sWard.add("sWard1");
                sWard.add("sWard2");
                sWard.add("sWard3");
                Set<String> cWard = new HashSet<>();
                cWard.add("cWard1");
                cWard.add("cWard2");
                cWard.add("cWard3");

                Set<Section> sections = new HashSet<>();
                Section mildSection = new Section("mild", "mDoctor", "mChief", mWard);
                sections.add(mildSection);
                Section severeSection = new Section("severe", "sDoctor", "sChief", sWard);
                sections.add(severeSection);
                Section criticalSection = new Section("critical", "cDoctor", "cChief", cWard);
                sections.add(criticalSection);
                sectionRepository.saveAll(sections);
            }

            private void StaffLoader(StaffRepository staffRepository) {
                Set<Staff> staff = new HashSet<>();
                Staff mDoctor = new Staff("mDoctor", "123456",
                        "1999-01-01", "mDoctor", "doctor", "mild");
                staff.add(mDoctor);
                Staff sDoctor = new Staff("sDoctor", "123456",
                        "1999-01-01", "sDoctor", "doctor", "severe");
                staff.add(sDoctor);
                Staff cDoctor = new Staff("cDoctor", "123456",
                        "1999-01-01", "cDoctor", "doctor", "critical");
                staff.add(cDoctor);

                Staff mChief = new Staff("mChief", "123456",
                        "2000-01-01", "mChief", "chief nurse", "mild");
                staff.add(mChief);
                Staff sChief = new Staff("sChief", "123456",
                        "2000-01-01", "sChief", "chief nurse", "severe");
                staff.add(sChief);
                Staff cChief = new Staff("cChief", "123456",
                        "2000-01-01", "cChief", "chief nurse", "critical");
                staff.add(cChief);

                Staff mWard1 = new Staff("mWard1", "123456",
                        "2000-01-01", "mWard1", "ward nurse", "mild");
                staff.add(mWard1);
                Staff mWard2 = new Staff("mWard2", "123456",
                        "2000-01-01", "mWard2", "ward nurse", "mild");
                staff.add(mWard2);
                Staff mWard3 = new Staff("mWard3", "123456",
                        "2000-01-01", "mWard3", "ward nurse", "mild");
                staff.add(mWard3);
                Staff mWard4 = new Staff("mWard4", "123456",
                        "2000-01-01", "mWard4", "ward nurse", "mild");
                staff.add(mWard4);

                Staff sWard1 = new Staff("sWard1", "123456",
                        "2000-01-01", "sWard1", "ward nurse", "severe");
                staff.add(sWard1);
                Staff sWard2 = new Staff("sWard2", "123456",
                        "2000-01-01", "sWard2", "ward nurse", "severe");
                staff.add(sWard2);
                Staff sWard3 = new Staff("sWard3", "123456",
                        "2000-01-01", "sWard3", "ward nurse", "severe");
                staff.add(sWard3);

                Staff cWard1 = new Staff("cWard1", "123456",
                        "2000-01-01", "cWard1", "ward nurse", "critical");
                staff.add(cWard1);
                Staff cWard2 = new Staff("cWard2", "123456",
                        "2000-01-01", "cWard2", "ward nurse", "critical");
                staff.add(cWard2);
                Staff cWard3 = new Staff("cWard3", "123456",
                        "2000-01-01", "cWard3", "ward nurse", "critical");
                staff.add(cWard3);

                // TODO：添加急诊护士
                staffRepository.saveAll(staff);
            }

            private void WardLoader(WardRepository wardRepository) {
                // TODO
            }

            private void PatientLoader(PatientRepository patientRepository) {
                Set<Patient> patients = new HashSet<>();
                Patient patient1 = new Patient("1", "mild", "mild", "mWard1", 1);
                patients.add(patient1);
                Patient patient2 = new Patient("2", "mild", "mild", "mWard1", 1);
                patients.add(patient2);
                Patient patient3 = new Patient("3", "mild", "mild", "mWard1", 1);
                patients.add(patient3);
                patientRepository.saveAll(patients);
            }
        };
    }
}
