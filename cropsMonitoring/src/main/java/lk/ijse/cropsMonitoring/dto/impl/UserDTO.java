package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements SuperDTO {
//    @Email
//    @NotBlank
    private String email;

//    @NotBlank
//    @Size(min = 8)
    private String password;

//    @NotNull
//    @Pattern(regexp = "OTHER|MANAGER|ADMINISTRATIVE|SCIENTIST")
    private String role;
}
