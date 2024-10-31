package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.customObj.StaffErrorResponse;
import lk.ijse.cropsMonitoring.customObj.StaffResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    private Gender gender;
    private Date dob;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String contact;
    private String email;
    private Date joinedDate;
    private List<String> vehicles;

    public enum Gender {
        MALE, FEMALE
    }
}
