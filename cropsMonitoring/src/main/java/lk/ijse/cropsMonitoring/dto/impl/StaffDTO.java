package lk.ijse.cropsMonitoring.dto.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.cropsMonitoring.customObj.StaffErrorResponse;
import lk.ijse.cropsMonitoring.customObj.StaffResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {

    private String id;

//    @NotBlank
//    @Pattern(regexp = "^[A-Z][a-z]*$")
    private String firstName;

//    @NotBlank
//    @Pattern(regexp = "^[A-Z][a-z]*$")
    private String lastName;

//    @NotBlank
    private String designation;

//    @NotBlank
    private String gender;

//    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String joinedDate;

//    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String DOB;

//    @NotBlank
    private String addressLine1;

//    @NotBlank
    private String addressLine2;

//    @NotBlank
    private String addressLine3;

    private String addressLine4;
    private String addressLine5;

//    @NotBlank
//    @Pattern(regexp = "^[0-9]{10}$")
    private String contactNo;

//    @NotBlank
//    @Email
    private String email;

//    @NotBlank
    private String role;


}
