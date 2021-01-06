package edu.fudan.database;

import edu.fudan.database.domain.Staff;
import edu.fudan.database.repository.PatientRepository;
import edu.fudan.database.repository.ReportRepository;
import edu.fudan.database.repository.StaffRepository;
import edu.fudan.database.repository.WardRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatabaseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseProjectApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(PatientRepository patientRepository,
                                        ReportRepository reportRepository,
                                        StaffRepository staffRepository,
                                        WardRepository wardRepository) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
//                Staff staff = new Staff("test", "pass", null, "name", "doctor", null);
//                staffRepository.save(staff);
            }
        };
    }
}
