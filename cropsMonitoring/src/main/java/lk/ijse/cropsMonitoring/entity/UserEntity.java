package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class UserEntity implements SuperEntity{

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

//    @OneToOne
//    @JoinColumn(name = "staff_id")
//    private StaffEntity staff;

    public enum Role {
        MANAGER, ADMINISTRATIVE, SCIENTIST, OTHER
    }

}
