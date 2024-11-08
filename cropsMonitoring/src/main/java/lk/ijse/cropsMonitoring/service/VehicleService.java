package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;

import java.util.List;

public interface VehicleService {
    void save(VehicleManagementDTO vehicleManagementDTO);
    void update(String id, VehicleManagementDTO vehicleManagementDTO);
    void delete(String id);
    VehicleResponse getSelectedVehicle(String id);
    List<VehicleManagementDTO> getAll();
}
