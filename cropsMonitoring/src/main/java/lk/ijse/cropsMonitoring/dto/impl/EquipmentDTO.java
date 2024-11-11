package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.customObj.EquipmentResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String equipmentId;
    private String name;
    private String type;
    private String status;
    private String assignedStaffId;
    private String assignedFieldCode;
}
