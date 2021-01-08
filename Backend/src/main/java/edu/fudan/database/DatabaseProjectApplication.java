package edu.fudan.database;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Section;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.domain.Ward;
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
                WardLoader(wardRepository);
                StaffLoader(staffRepository);
                PatientLoader(patientRepository);
            }

            private void SectionLoader(SectionRepository sectionRepository) {
                Set<String> mWardNurse = new HashSet<>();
                mWardNurse.add("mWard1");
                mWardNurse.add("mWard2");
                mWardNurse.add("mWard3");
                mWardNurse.add("mWard4");
                Set<String> sWardNurse = new HashSet<>();
                sWardNurse.add("sWard1");
                sWardNurse.add("sWard2");
                sWardNurse.add("sWard3");
                Set<String> cWardNurse = new HashSet<>();
                cWardNurse.add("cWard1");
                cWardNurse.add("cWard2");
                cWardNurse.add("cWard3");

                Set<String> mWard = new HashSet<>();
                mWard.add("轻症病房1");
                mWard.add("轻症病房2");
                mWard.add("轻症病房3");
                mWard.add("轻症病房4");
                mWard.add("轻症病房5");
                mWard.add("轻症病房6");
                Set<String> sWard = new HashSet<>();
                sWard.add("重症病房1");
                sWard.add("重症病房2");
                sWard.add("重症病房3");
                sWard.add("重症病房4");
                sWard.add("重症病房5");
                sWard.add("重症病房6");
                Set<String> cWard = new HashSet<>();
                cWard.add("危重症病房1");
                cWard.add("危重症病房2");
                cWard.add("危重症病房3");
                cWard.add("危重症病房4");
                cWard.add("危重症病房5");
                cWard.add("危重症病房6");

                Section mildSection = new Section("mild", "mDoctor", "mChief", mWardNurse, mWard);
                sectionRepository.save(mildSection);
                Section severeSection = new Section("severe", "sDoctor", "sChief", sWardNurse, sWard);
                sectionRepository.save(severeSection);
                Section criticalSection = new Section("critical", "cDoctor", "cChief", cWardNurse, cWard);
                sectionRepository.save(criticalSection);
            }

            private void WardLoader(WardRepository wardRepository) {
                Set<String> mPatients = new HashSet<>();
                mPatients.add("张三");
                mPatients.add("李四");
                mPatients.add("王五");
                mPatients.add("赵六");
                Set<Integer> mSickbeds = new HashSet<>();
                mSickbeds.add(1);
                mSickbeds.add(2);
                mSickbeds.add(3);
                mSickbeds.add(4);

                Ward mWard1 = new Ward("mild", "轻症病房1", 4, mPatients, mSickbeds);
                wardRepository.save(mWard1);
                Ward mWard2 = new Ward("mild", "轻症病房2", 4, new HashSet<>(), new HashSet<>());
                wardRepository.save(mWard2);
                Ward mWard3 = new Ward("mild", "轻症病房3", 4, new HashSet<>(), new HashSet<>());
                wardRepository.save(mWard3);
                Ward mWard4 = new Ward("mild", "轻症病房4", 4, new HashSet<>(), new HashSet<>());
                wardRepository.save(mWard4);
                Ward mWard5 = new Ward("mild", "轻症病房5", 4, new HashSet<>(), new HashSet<>());
                wardRepository.save(mWard5);
                Ward mWard6 = new Ward("mild", "轻症病房6", 4, new HashSet<>(), new HashSet<>());
                wardRepository.save(mWard6);

                Set<String> sPatients = new HashSet<>();
                mPatients.add("重一");
                Set<Integer> sSickbeds = new HashSet<>();
                mSickbeds.add(1);

                Ward sWard1 = new Ward("severe", "重症病房1", 2, sPatients, sSickbeds);
                wardRepository.save(sWard1);
                Ward sWard2 = new Ward("severe", "重症病房2", 2, new HashSet<>(), new HashSet<>());
                wardRepository.save(sWard2);
                Ward sWard3 = new Ward("severe", "重症病房3", 2, new HashSet<>(), new HashSet<>());
                wardRepository.save(sWard3);
                Ward sWard4 = new Ward("severe", "重症病房4", 2, new HashSet<>(), new HashSet<>());
                wardRepository.save(sWard4);
                Ward sWard5 = new Ward("severe", "重症病房5", 2, new HashSet<>(), new HashSet<>());
                wardRepository.save(sWard5);
                Ward sWard6 = new Ward("severe", "重症病房6", 2, new HashSet<>(), new HashSet<>());
                wardRepository.save(sWard6);

                Set<String> cPatients = new HashSet<>();
                mPatients.add("危重一");
                Set<Integer> cSickbeds = new HashSet<>();
                mSickbeds.add(1);

                Ward cWard1 = new Ward("severe", "危重症病房1", 1, cPatients, cSickbeds);
                wardRepository.save(cWard1);
                Ward cWard2 = new Ward("severe", "危重症病房2", 1, new HashSet<>(), new HashSet<>());
                wardRepository.save(cWard2);
                Ward cWard3 = new Ward("severe", "危重症病房3", 1, new HashSet<>(), new HashSet<>());
                wardRepository.save(cWard3);
                Ward cWard4 = new Ward("severe", "危重症病房4", 1, new HashSet<>(), new HashSet<>());
                wardRepository.save(cWard4);
                Ward cWard5 = new Ward("severe", "危重症病房5", 1, new HashSet<>(), new HashSet<>());
                wardRepository.save(cWard5);
                Ward cWard6 = new Ward("severe", "危重症病房6", 1, new HashSet<>(), new HashSet<>());
                wardRepository.save(cWard6);
            }

            private void StaffLoader(StaffRepository staffRepository) {
                Staff mDoctor = new Staff("mDoctor", "123456",
                        "1999-01-01", "mDoctor", "doctor", "mild");
                staffRepository.save(mDoctor);
                Staff sDoctor = new Staff("sDoctor", "123456",
                        "1999-01-01", "sDoctor", "doctor", "severe");
                staffRepository.save(sDoctor);
                Staff cDoctor = new Staff("cDoctor", "123456",
                        "1999-01-01", "cDoctor", "doctor", "critical");
                staffRepository.save(cDoctor);

                Staff mChief = new Staff("mChief", "123456",
                        "2000-01-01", "mChief", "chief nurse", "mild");
                staffRepository.save(mChief);
                Staff sChief = new Staff("sChief", "123456",
                        "2000-01-01", "sChief", "chief nurse", "severe");
                staffRepository.save(sChief);
                Staff cChief = new Staff("cChief", "123456",
                        "2000-01-01", "cChief", "chief nurse", "critical");
                staffRepository.save(cChief);

                Staff mWard1 = new Staff("mWard1", "123456",
                        "2000-01-01", "mWard1", "ward nurse", "mild");
                staffRepository.save(mWard1);
                Staff mWard2 = new Staff("mWard2", "123456",
                        "2000-01-01", "mWard2", "ward nurse", "mild");
                staffRepository.save(mWard2);
                Staff mWard3 = new Staff("mWard3", "123456",
                        "2000-01-01", "mWard3", "ward nurse", "mild");
                staffRepository.save(mWard3);
                Staff mWard4 = new Staff("mWard4", "123456",
                        "2000-01-01", "mWard4", "ward nurse", "mild");
                staffRepository.save(mWard4);

                Staff sWard1 = new Staff("sWard1", "123456",
                        "2000-01-01", "sWard1", "ward nurse", "severe");
                staffRepository.save(sWard1);
                Staff sWard2 = new Staff("sWard2", "123456",
                        "2000-01-01", "sWard2", "ward nurse", "severe");
                staffRepository.save(sWard2);
                Staff sWard3 = new Staff("sWard3", "123456",
                        "2000-01-01", "sWard3", "ward nurse", "severe");
                staffRepository.save(sWard3);

                Staff cWard1 = new Staff("cWard1", "123456",
                        "2000-01-01", "cWard1", "ward nurse", "critical");
                staffRepository.save(cWard1);
                Staff cWard2 = new Staff("cWard2", "123456",
                        "2000-01-01", "cWard2", "ward nurse", "critical");
                staffRepository.save(cWard2);
                Staff cWard3 = new Staff("cWard3", "123456",
                        "2000-01-01", "cWard3", "ward nurse", "critical");
                staffRepository.save(cWard3);

                Staff backupWard1 = new Staff("backupWard1", "123456",
                        "2000-02-02", "backupWard1", "ward nurse", "backup");
                staffRepository.save(backupWard1);
                Staff backupWard2 = new Staff("backupWard2", "123456",
                        "2000-02-02", "backupWard2", "ward nurse", "backup");
                staffRepository.save(backupWard2);
                Staff backupWard3 = new Staff("backupWard3", "123456",
                        "2000-02-02", "backupWard3", "ward nurse", "backup");
                staffRepository.save(backupWard3);
                Staff backupWard4 = new Staff("backupWard4", "123456",
                        "2000-02-02", "backupWard4", "ward nurse", "backup");
                staffRepository.save(backupWard4);
                Staff backupWard5 = new Staff("backupWard5", "123456",
                        "2000-02-02", "backupWard5", "ward nurse", "backup");
                staffRepository.save(backupWard5);
                Staff backupWard6 = new Staff("backupWard6", "123456",
                        "2000-02-02", "backupWard6", "ward nurse", "backup");
                staffRepository.save(backupWard6);

                Staff eNurse = new Staff("eNurse", "123456",
                        "2000-03-03", "eNurse", "emergency nurse", null);
                staffRepository.save(eNurse);
            }

            private void PatientLoader(PatientRepository patientRepository) {
                Patient patient1 = new Patient("张三", "male", 40, "mild", false, "mild", "mWard1", "轻症病房1", 1, 0);
                patientRepository.save(patient1);
                Patient patient2 = new Patient("李四", "female", 39, "mild", false, "mild", "mWard1", "轻症病房1", 2, 0);
                patientRepository.save(patient2);
                Patient patient3 = new Patient("王五", "female", 25, "mild", false, "mild", "mWard1", "轻症病房1", 3, 0);
                patientRepository.save(patient3);
                Patient patient4 = new Patient("赵六", "male", 28, "mild", false, "mild", "mWard2", "轻症病房1", 4, 0);
                patientRepository.save(patient4);

                Patient patient5 = new Patient("重一", "male", 24, "severe", false, "severe", "sWard1", "重症病房1", 1, 0);
                patientRepository.save(patient5);
                Patient patient6 = new Patient("危重一", "male", 30, "critical", false, "critical", "cWard1", "危重症病房1", 1, 0);
                patientRepository.save(patient6);
            }
        };
    }
}
