package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class EquipmentEntity implements SuperEntity{
    @Id
    @Column(name = "eqiupment_id")
    private String equipmentId;
    @Column(name = "equipment_name")
    private String equipmentName;
    @Column(name = "equipment_type")
    private String equipmentType;
    @Column(name = "availability_status")
    private String status;

    @ManyToOne(optional = true)
    @JoinColumn(name = "field_code", referencedColumnName = "field_code")
    @ToString.Exclude
    private FieldEntity field;

    @OneToOne(optional = true)
    @JoinColumn(name = "staff_member_id", referencedColumnName = "staff_member_id")
    @ToString.Exclude
    private StaffEntity staff;

}
