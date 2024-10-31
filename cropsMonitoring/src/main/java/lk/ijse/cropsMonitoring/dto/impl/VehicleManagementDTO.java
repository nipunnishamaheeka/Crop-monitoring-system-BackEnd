package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleManagementDTO implements SuperDTO, VehicleResponse {
    private String vehicleCode;
    private String vehicleMainNumber;
    private String vehicleCategory;
    private String fuelType;
    private String type;
    private String allocatedStaffMemberId;
    private String remarks;
}
