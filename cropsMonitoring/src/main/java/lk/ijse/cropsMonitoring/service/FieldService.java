package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;

import java.util.List;

public interface FieldService {
    void save(FieldDTO fieldDTO);
    void update(FieldDTO fieldDTO,List<String> StaffIds);
    void delete(String fieldCode);
    FieldResponse getSelectedField(String fieldCode);
    List<FieldDTO> getAll();
}
