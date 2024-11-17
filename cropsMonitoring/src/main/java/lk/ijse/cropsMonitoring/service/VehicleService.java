package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;

import java.util.List;

public interface VehicleService {
    void save(VehicleManagementDTO vehicleManagementDTO);
    void update(VehicleManagementDTO vehicleDTO , String staffId , String vehicleCode);
    void delete(String vehicleCode);
    VehicleResponse getSelectedVehicle(String vehicleCode);
    List<VehicleManagementDTO> getAll();
}
