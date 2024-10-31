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
    private String userName;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public enum UserRole {
        MANAGER, ADMINISTRATIVE, SCIENTIST, OTHER
    }

}
