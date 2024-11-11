package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lk.ijse.cropsMonitoring.dto.impl.StaffDTO;
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
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Enums.Role role;

    @Enumerated(EnumType.STRING)
    private Enums.Gender gender;

    @Temporal(TemporalType.DATE)
    private Date joinedDate;

    @Temporal(TemporalType.DATE)
    private Date dob;


    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String contactNo;
    private String email;

    @ManyToMany(mappedBy = "staff")
    private List<FieldEntity> fields;

    @ManyToMany
    @JoinTable(
            name = "staff_vehicle",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "v_code")
    )
    private List<VehicleManagementEntity> vehicles;



}
