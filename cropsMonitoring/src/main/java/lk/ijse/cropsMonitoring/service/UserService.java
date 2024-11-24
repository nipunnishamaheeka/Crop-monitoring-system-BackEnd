package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.UserResponse;
import lk.ijse.cropsMonitoring.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void saveUser(UserDTO user);

    UserResponse getUserByEmail(String email);

    void updateUser(UserDTO user , String email);

    UserDetailsService userDetailsService();

}
