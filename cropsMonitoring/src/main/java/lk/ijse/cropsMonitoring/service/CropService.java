package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;

import java.util.List;

public interface CropService {
    void save(CropDTO cropDTO,String fieldCode);
    void update(String id, CropDTO cropDTO,String fieldCode);
    void delete(String id);
    CropResponse getSelectedCrops(String id);
    List<CropDTO> getAll();
}
