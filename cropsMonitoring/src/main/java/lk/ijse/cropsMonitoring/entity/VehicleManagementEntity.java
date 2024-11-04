package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vehicle_management")

public class VehicleManagementEntity implements SuperEntity{
    @Id
    @Column(name = "v_code")
    private String vehicleCode;

    @Column(name = "license_plate_no")
    private String licensePlateNo;

    @Column(name = "category")
    private String vehicleCategory;

    @Column(name = "fuel_type")
    private String fuelType;

    private String status;

    private String remarks;

//    @ManyToOne
//    @JoinColumn(name = "staff_id")
//    private StaffEntity staff;


}
