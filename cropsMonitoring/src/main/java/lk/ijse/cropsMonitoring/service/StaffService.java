package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.customObj.StaffResponse;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.dto.impl.StaffDTO;

import java.util.List;

public interface StaffService {
    void save(StaffDTO staffDTO);
    void update(String id, StaffDTO staffDTO);
    void delete(String id);
    StaffResponse getSelectedStaff(String id);
    List<StaffDTO> getAll();
}
