package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;

import java.util.List;

public interface VehicleService {
    void save(VehicleManagementDTO vehicleManagementDTO);
    void update(String id, VehicleManagementDTO vehicleManagementDTO);
    void delete(String id);
    CropResponse getSelectedVehicle(String id);
    List<VehicleManagementDTO> getAll();
}
