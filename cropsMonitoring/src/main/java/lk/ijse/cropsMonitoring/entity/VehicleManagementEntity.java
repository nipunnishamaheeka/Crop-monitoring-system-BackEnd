package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private String vehicleCode;
    private String vehicleMainNumber;
    private String vehicleCategory;
    private String fuelType;
    private String type;

    @ManyToOne
    private StaffEntity allocatedStaffMember;

    private String remarks;
}
