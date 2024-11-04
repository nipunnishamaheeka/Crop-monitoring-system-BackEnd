package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity implements SuperEntity{
    @Id
    @Column(name = "staff_id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String designation;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "joined_date")
    private Date joinedDate;

    private Date dob;


    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String contact;
    private String email;


//    @OneToOne(mappedBy = "staff", cascade = CascadeType.ALL)
//    private UserEntity user;
//
//    @ManyToMany(mappedBy = "staffList")
//    private List<FieldEntity> fields;
//
//    @OneToMany(mappedBy = "staff")
//    private List<VehicleManagementEntity> vehicles;

    // Enum for Gender
    public enum Gender {
        MALE, FEMALE
    }

}
