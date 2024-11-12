package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.dao.FieldDAO;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import lk.ijse.cropsMonitoring.entity.VehicleManagementEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.FieldService;
import lk.ijse.cropsMonitoring.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDAO fieldDAO;
    @Autowired
    private Mapping mapping;

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);


    public FieldServiceImpl(FieldDAO fieldDAO) {
        this.fieldDAO = fieldDAO;
    }

@Override
public void save(FieldDTO fieldDTO) {
    if (fieldDTO == null) {
        throw new IllegalArgumentException("FieldDTO cannot be null");
    }
    fieldDTO.setCode(generateFieldID());
    FieldEntity entity = mapping.toFieldEntity(fieldDTO);
    FieldEntity savedEntity = fieldDAO.save(entity);
    logger.info("Saved field entity: {}", savedEntity);
    System.out.println("Saved entity = " + savedEntity);

    if (savedEntity == null || savedEntity.getCode() == null) {
        throw new DataPersistFailedException("Failed to save the field entity");
    }
}
    @Override
    @Transactional
    public void update(String id, FieldDTO fieldDTO) {
        Optional<FieldEntity> fieldEntity = fieldDAO.findById(id);
        if (fieldEntity.isPresent()) {
            fieldEntity.get().setName(fieldDTO.getName());
            fieldEntity.get().setLocation(fieldDTO.getLocation());
            fieldEntity.get().setSize_of_Field(fieldDTO.getSize_of_Field());
            fieldEntity.get().setFieldImage1(fieldDTO.getFieldImage1());
  fieldEntity.get().setFieldImage2(fieldDTO.getFieldImage2());
        //    fieldEntity.get().setCrops(fieldDTO.getCrops());
        }else {
            throw new DataPersistFailedException("Failed To Update");
        }
    }
    @Override
    public void delete(String id) {
        if (fieldDAO.existsById(id)) {
            fieldDAO.deleteById(id);
        } else {
            throw new DataPersistFailedException("Failed To Delete");
        }
    }

    @Override
    public FieldResponse getSelectedField(String id) {

        FieldEntity fieldEntity = fieldDAO.findById(id).orElse(null);
        if (fieldEntity == null) {
            return mapping.toFieldDto(fieldEntity);
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
