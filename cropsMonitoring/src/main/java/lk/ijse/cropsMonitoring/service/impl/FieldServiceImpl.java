package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.dao.CropDAO;
import lk.ijse.cropsMonitoring.dao.FieldDAO;
import lk.ijse.cropsMonitoring.dao.StaffDAO;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.dto.impl.StaffDTO;
import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import lk.ijse.cropsMonitoring.entity.StaffEntity;
import lk.ijse.cropsMonitoring.entity.VehicleManagementEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.FieldService;
import lk.ijse.cropsMonitoring.util.AppUtil;
import lk.ijse.cropsMonitoring.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldDAO fieldDAO;
    private final Mapping mapping;
    private final StaffDAO staffDAO;
    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
@Override
public void save(FieldDTO fieldDTO) {
//    if (fieldDTO == null) {
//        throw new IllegalArgumentException("FieldDTO cannot be null");
//    }
//    fieldDTO.setFieldCode(generateFieldID());
//    List<StaffEntity> staffList = new ArrayList<>();
//    for (String staffId : fieldDTO.getStaffId()) {
//        staffDAO.findById(staffId).ifPresent(staffList::add);
//    }
////    List<CropEntity> cropList = new ArrayList<>();
////    for (String cropCode : fieldDTO.getCropCodes()) {
////        cropDAO.findById(cropCode).ifPresent(cropList::add);
////    }
//    FieldEntity entity = mapping.toFieldEntity(fieldDTO);
//
//    entity.setStaff(staffList);
////    entity.setCrops(cropList);
//    FieldEntity savedEntity = fieldDAO.save(entity);
//    logger.info("Saved field entity: {}", savedEntity);
//    System.out.println("Saved entity = " + savedEntity);
//
//    if (savedEntity == null || savedEntity.getCode() == null) {
//        throw new DataPersistFailedException("Failed to save the field entity");
//    }

        fieldDTO.setFieldCode(generateFieldID());
    FieldEntity save = fieldDAO.save( mapping.toFieldEntity(fieldDTO));
    if (save == null){
        throw new DataPersistFailedException("Field save failed");
    }
}
    @Override
    public void update(FieldDTO fieldDTO, List<String> staffIds) {
        Optional<FieldEntity> fieldEntity = fieldDAO.findById(fieldDTO.getFieldCode());
        if (fieldEntity.isPresent()) {
            FieldEntity TempField = mapping.toFieldEntity(fieldDTO);
            List<StaffEntity> staff = new ArrayList<>();

            for (String staffId : staffIds) {
                Optional<StaffEntity> optional = staffDAO.findById(staffId);
                optional.ifPresent(staff::add);
            }
            TempField.setStaff(staff);
            FieldEntity save = fieldDAO.save(TempField);
            if (save == null) {
                throw new DataPersistFailedException("Field update failed");
            }
        } else {
            throw new DataPersistFailedException("Field not found");

        }
    }
    @Override
    public void delete(String fieldCode) {
        if (fieldDAO.existsById(fieldCode)) {
            fieldDAO.deleteById(fieldCode);
        } else {
            throw new DataPersistFailedException("Failed To Delete");
        }
    }

    @Override
    public FieldResponse getSelectedField(String fieldCode) {
Optional<FieldEntity> fieldEntity = fieldDAO.findById(fieldCode);


        if (fieldEntity.isPresent()) {
            FieldDTO fieldDTO = mapping.toFieldDto(fieldEntity.get());
            List<String> staffIds = new ArrayList<>();
            fieldEntity.get().getStaff().forEach(
                    staff -> staffIds.add(staff.getId())
            );
            fieldDTO.setStaffId(staffIds);
            return fieldDTO;
        }else {
            throw new DataPersistFailedException("Failed To Get");
        }
    }

    @Override
    public List<FieldDTO> getAll() {
        return mapping.toFieldDtoList(fieldDAO.findAll());
    }

    private String generateFieldID() {
        long fieldCount = fieldDAO.count();
        if (fieldCount == 0) {
            return "F001";
        } else {

            String lastId = fieldDAO.findLastFieldCode();
            if (lastId != null && lastId.startsWith("F")) {

                try {
                    int newId = Integer.parseInt(lastId.substring(1)) + 1;
                    return String.format("F%03d", newId);
                } catch (NumberFormatException e) {

                    logger.error("Failed to parse ID: {}", lastId, e);
                }
            }
            return "F001";
        }
    }
}
