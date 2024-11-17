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

//    @NotBlank
//    @Pattern(regexp = "^[A-Z0-9-]+$")
    private String licensePlateNumber;

//    @NotBlank
    private String vehicleCategory;

//    @NotBlank
    private String fuelType;

//    @NotBlank
    private String status;

//    @Size(max = 200)
    private String remarks;

    private String staffId;
}
