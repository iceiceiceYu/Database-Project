package edu.fudan.database.service;

import edu.fudan.database.domain.Staff;
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

    public Staff info(String username) {
        return staffRepository.findStaffByUsername(username);
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

    public String modifyInfo(String username, String birth, String name) {
        Staff staff = staffRepository.findStaffByUsername(username);
        if (staff != null) {
            staff.setBirth(birth);
            staff.setName(name);
            staffRepository.save(staff);
            return "success";
        }
        return "error";
    }

    public String modifyPass(String username, String oldPassword, String newPassword) {
        Staff staff = staffRepository.findStaffByUsername(username);
        if (staff != null) {
            if (staff.getPassword().equals(oldPassword)) {
                staff.setPassword(newPassword);
                staffRepository.save(staff);
                return "success";
            } else {
                return "wrong password";
            }
        }
        return "error";
    }
}
