package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.customObj.EquipmentResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String equipmentId;
    //@NotBlank
    private String equipmentName;
    //@NotBlank
//    @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
    private String equipmentType;
//    @NotBlank
    private String status;
    private String fieldCode;
    private String staffId;
}
