package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.EquipmentResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.EquipmentDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;

import java.util.List;

public interface EquipmentService {
    void save(EquipmentDTO equipmentDTO);
    void update(EquipmentDTO equipmentDTO,String staffId,String fieldCode,String equipmentId);
    void delete(String equipmentId);
    EquipmentResponse getSelectedEquipment(String equipmentId);
    List<EquipmentDTO> getAll();

}
