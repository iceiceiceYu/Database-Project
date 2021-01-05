package edu.fudan.database.service;

import edu.fudan.database.domain.Staff;
import edu.fudan.database.domain.User;
import edu.fudan.database.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final StaffRepository staffRepository;

    @Autowired
    public UserService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff login(String username, String password) {
        Staff staff = staffRepository.findStaffByUsername(username);
        if (staff != null) {
            String correctPassword = staff.getPassword();
            if (correctPassword.equals(password)) {
                return staff;
            }
        }
        return null;
    }
}
