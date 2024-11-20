package lk.ijse.cropsMonitoring.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.cropsMonitoring.customObj.CropDetailErrorResponse;
import lk.ijse.cropsMonitoring.customObj.CropDetailResponse;
import lk.ijse.cropsMonitoring.dao.CropDAO;
import lk.ijse.cropsMonitoring.dao.CropDetailsDAO;
import lk.ijse.cropsMonitoring.dao.FieldDAO;
import lk.ijse.cropsMonitoring.dao.StaffDAO;
import lk.ijse.cropsMonitoring.dto.impl.CropDetailsDTO;
import lk.ijse.cropsMonitoring.entity.CropDetailsEntity;
import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import lk.ijse.cropsMonitoring.entity.StaffEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.CropDetailsService;
import lk.ijse.cropsMonitoring.util.AppUtil;
import lk.ijse.cropsMonitoring.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CropDetailsServiceImpl  implements CropDetailsService {
    private final FieldDAO fieldDAO;
    private final StaffDAO staffDAO;
    private final CropDAO cropDAO;

    private final Mapping mapping;
    private final CropDetailsDAO cropDetailsDAO;

    @Override
    public void saveCropDetails(CropDetailsDTO cropDetailsDTO) {
        List<FieldEntity> filed = new ArrayList<>();
        List<CropEntity> crops = new ArrayList<>();
        List<StaffEntity> staff = new ArrayList<>();

        for (String fieldCode : cropDetailsDTO.getFieldCodes()) {
            fieldDAO.findById(fieldCode).ifPresent(filed::add);
        }

        for (String cropCode : cropDetailsDTO.getCropCodes()) {
            cropDAO.findById(cropCode).ifPresent(crops::add);
        }

        for (String staffId : cropDetailsDTO.getStaffIds()) {
            staffDAO.findById(staffId).ifPresent(staff::add);
        }

//        String logCode = AppUtil.createCropDetailsID();
        cropDetailsDTO.setLogCode(generateLogID());
//        cropDetailsDTO.setLogCode(logCode);
        CropDetailsEntity cropDetails = mapping.convertCropDetailsDTOToCropDetails(cropDetailsDTO);
        cropDetails.setField(filed);
        cropDetails.setCrop(crops);
        cropDetails.setStaff(staff);
        CropDetailsEntity save = cropDetailsDAO.save(cropDetails);
        if (save == null){
            throw new DataPersistFailedException("Crop details save failed");
        }
    }

    @Override
    public void updateCropDetails(CropDetailsDTO cropDetailsDTO, String logCode) {
        Optional<CropDetailsEntity> cropDetailsByLogCode = cropDetailsDAO.findById(logCode);
        if (cropDetailsByLogCode.isPresent()){
            cropDetailsByLogCode.get().setLogDetails(cropDetailsDTO.getLogDetails());
            cropDetailsByLogCode.get().setObservedImage(cropDetailsDTO.getObservedImage());
        }else {
            throw new NotFoundException("Crop details not found");
        }
    }

    @Override
    public CropDetailResponse findCropDetailsByLogCode(String logCode) {
        Optional<CropDetailsEntity> cropDetailsByLogCode = cropDetailsDAO.findById(logCode);
        if (cropDetailsByLogCode.isPresent()){
            CropDetailsDTO cropDetailsDTO = mapping.convertCropDetailsToCropDetailsDTO(cropDetailsByLogCode.get());
            if (cropDetailsByLogCode.get().getField() != null){
                List<String> fieldCodes = new ArrayList<>();
                cropDetailsByLogCode.get().getField().forEach(
                        field -> fieldCodes.add(field.getCode())
                );
                cropDetailsDTO.setFieldCodes(fieldCodes);
            }
            if (cropDetailsByLogCode.get().getCrop() != null){
                List<String> cropCodes = new ArrayList<>();
                cropDetailsByLogCode.get().getCrop().forEach(
                        crop -> cropCodes.add(crop.getCropCode())
                );
                cropDetailsDTO.setCropCodes(cropCodes);
            }
            if (cropDetailsByLogCode.get().getStaff() != null){
                List<String> staffIds = new ArrayList<>();
                cropDetailsByLogCode.get().getStaff().forEach(
                        staff -> staffIds.add(staff.getId())
                );
                cropDetailsDTO.setStaffIds(staffIds);
            }
            return cropDetailsDTO;
        }else {
            return new CropDetailErrorResponse(0,"Crop details not found");
        }
    }

    @Override
    public void deleteCropDetailsByLogCode(String logCode) {
        Optional<CropDetailsEntity> cropDetailsByLogCode = cropDetailsDAO.findById(logCode);
        if (cropDetailsByLogCode.isPresent()) {
            cropDetailsDAO.delete(cropDetailsByLogCode.get());
        }else {
            throw new NotFoundException("Crop details not found");
        }
    }

    @Override
    public List<CropDetailsDTO> getAllCropDetails() {
        List<CropDetailsEntity> all = cropDetailsDAO.findAll();
        return mapping.convertCropDetailsListToCropDetailsDTOList(all);

    }

    private String generateLogID() {
        long logCount = cropDetailsDAO.count();
        if (logCount == 0) {
            return "CD001";
        } else {

            String lastId = cropDetailsDAO.findLastLogCode(logCount);
            if (lastId != null && lastId.startsWith("CD")) {

                try {
                    int newId = Integer.parseInt(lastId.substring(1)) + 1;
                    return String.format("CD%03d", newId);
                } catch (NumberFormatException e) {

                    log.error("Failed to parse crop ID: {}", lastId, e);
                }
            }
            return "CD001";
        }
    }
}
