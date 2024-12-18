package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dao.CropDAO;
import lk.ijse.cropsMonitoring.dao.FieldDAO;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.CropService;
import lk.ijse.cropsMonitoring.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {

    private final CropDAO cropDAO;
private final FieldDAO fieldDAO;
    private final Mapping mapping;
    private static final Logger logger = LoggerFactory.getLogger(CropServiceImpl.class);

    @Override
    public void save(CropDTO cropDTO, String fieldCode) {
        // Generate the crop code
        cropDTO.setCropCode(generateCropID());
        logger.info("CropDTO received: {}", cropDTO);

        // Convert CropDTO to CropEntity
        CropEntity entity = mapping.toCropsEntity(cropDTO);

        // Retrieve the FieldEntity associated with the fieldCode
        FieldEntity fieldEntity = fieldDAO.findById(fieldCode)
                .orElseThrow(() -> new DataPersistFailedException("Field not found for code: " + fieldCode));
        logger.info("Retrieved FieldEntity: {}", fieldEntity);

        // Associate the field with the crop
        entity.setField(fieldEntity);

        // Save the crop entity
        CropEntity savedEntity = cropDAO.save(entity);
        logger.info("Saved CropEntity: {}", savedEntity);

        // Verify the save operation
        if (savedEntity == null) {
            throw new DataPersistFailedException("Failed to save the crop entity");
        }
    }


    @Override
    public void update(String id, CropDTO cropDTO, String fieldCode) {
        Optional<CropEntity> cropEntity = cropDAO.findById(id);
        if (cropEntity.isPresent()) {
            FieldEntity field = fieldDAO.findById(fieldCode).orElseThrow(
                    () -> new NotFoundException("Field not found")
            );
            cropEntity.get().setField(field);
            cropEntity.get().setCropImage(cropDTO.getCropImage());
            cropEntity.get().setCropSeason(cropDTO.getCropSeason());
            cropEntity.get().setCategory(cropDTO.getCategory());
            cropEntity.get().setCropCommonName(cropDTO.getCropCommonName());
            cropEntity.get().setCropScientificName(cropDTO.getCropScientificName());



        }else {
        throw new NotFoundException("Failed To Update");
        }
    }

    @Override
    public void delete(String id) {

        if (cropDAO.existsById(id)) {
            cropDAO.deleteById(id);
        } else {
            throw new DataPersistFailedException("Failed To Delete");
        }
    }

    @Override
    public CropResponse getSelectedCrops(String id) {
        CropEntity cropEntity = cropDAO.findById(id).orElse(null);
        if (cropEntity != null) {
            return mapping.toCropsDto(cropEntity);

        } else {
            throw new DataPersistFailedException("Failed To get");
        }
    }

    @Override
    public List<CropDTO> getAll() {
        return mapping.toCropsDtoList(cropDAO.findAll());
    }

    private String generateCropID() {
        long cropCount = cropDAO.count();
        if (cropCount == 0) {
            return "C001";
        } else {

            String lastId = cropDAO.findLastCropCode();
            if (lastId != null && lastId.startsWith("C")) {

                try {
                    int newId = Integer.parseInt(lastId.substring(1)) + 1;
                    return String.format("C%03d", newId);
                } catch (NumberFormatException e) {

                    logger.error("Failed to parse crop ID: {}", lastId, e);
                }
            }
            return "C001";
        }
    }
}
