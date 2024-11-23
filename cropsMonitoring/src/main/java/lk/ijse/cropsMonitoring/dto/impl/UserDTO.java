package lk.ijse.cropsMonitoring.dto.impl;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class  UserDTO implements SuperDTO {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

//    @NotBlank
//    @Size(min = 8)
    private String password;

//    @NotNull
//    @Pattern(regexp = "OTHER|MANAGER|ADMINISTRATIVE|SCIENTIST")
    private String role;
}
