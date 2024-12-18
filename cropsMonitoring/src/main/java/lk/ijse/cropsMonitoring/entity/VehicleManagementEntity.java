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
@Table(name = "vehicle_management")

public class VehicleManagementEntity implements SuperEntity{
    @Id
    @Column(name = "vehicle_code")
    private String vehicleCode;
    @Column(name = "licence_plate_number", unique = true)
    private String licensePlateNumber;
    @Column(name = "vehicle_category")
    private String vehicleCategory;
    @Column(name = "fuel_type")
    private String fuelType;
    @Column(name = "status")
    private String status;
    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "staff_member_id", referencedColumnName = "staff_member_id")
    @ToString.Exclude
    private StaffEntity staff;



}
