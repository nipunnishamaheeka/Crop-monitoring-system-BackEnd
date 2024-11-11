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
    private String licensePlateNo;
    private String vehicleCategory;
    private String fuelType;
    private String status;

    private String remarks;
    private String allocatedStaffId;

}
