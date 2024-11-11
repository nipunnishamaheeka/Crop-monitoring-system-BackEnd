package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.EquipmentResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.EquipmentDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;

import java.util.List;

public interface EquipmentService {
    void save(EquipmentDTO equipmentDTO);
    void update(String id, EquipmentDTO equipmentDTO);
    void delete(String id);
    EquipmentResponse getSelectedEquipment(String id);
    List<EquipmentDTO> getAll();

}
