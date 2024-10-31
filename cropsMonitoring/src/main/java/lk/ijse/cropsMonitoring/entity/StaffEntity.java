package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity implements SuperEntity{
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dob;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String contact;
    private String email;
    private Date joinedDate;

    @ElementCollection
    private List<String> vehicles;

    // Enum for Gender
    public enum Gender {
        MALE, FEMALE
    }

}
