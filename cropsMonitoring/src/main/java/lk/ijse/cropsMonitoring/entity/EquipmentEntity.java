package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class EquipmentEntity implements SuperEntity{
    @Id
    private String equipmentId;
    private String name;

//   @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private String type;

//    @Enumerated(EnumType.STRING)
    private String status;

    @OneToOne
    @JoinColumn(name = "staff_id")
    private StaffEntity assignedStaff;

    @OneToOne
    @JoinColumn(name = "field_code")
    private FieldEntity assignedField;

}
