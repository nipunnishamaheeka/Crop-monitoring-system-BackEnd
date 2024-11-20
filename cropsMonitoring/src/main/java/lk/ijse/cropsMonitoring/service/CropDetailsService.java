package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.customObj.CropDetailResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDetailsDTO;

import java.util.List;

public interface CropDetailsService {
    void saveCropDetails(CropDetailsDTO cropDetailsDTO);

    void updateCropDetails(CropDetailsDTO cropDetailsDTO , String logCode);

    CropDetailResponse findCropDetailsByLogCode(String logCode);

    void deleteCropDetailsByLogCode(String logCode);

    List<CropDetailsDTO> getAllCropDetails();
}
