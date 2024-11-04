package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements SuperDTO {
    private String email;
    private String password;

    private String staffId;
    private UserRole role;

    public enum UserRole {
        MANAGER, ADMINISTRATIVE, SCIENTIST, OTHER
    }
}
