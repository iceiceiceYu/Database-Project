package edu.fudan.database.service;

import edu.fudan.database.domain.User;
import edu.fudan.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String login(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return "wrong username";
        } else {
            String correctPassword = user.getPassword();
            if (correctPassword.equals(password)) {
                return "success";
            } else {
                return "wrong password";
            }
        }
    }
}
