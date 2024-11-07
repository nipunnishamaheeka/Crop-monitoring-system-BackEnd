package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dao.CropDAO;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.CropService;
import lk.ijse.cropsMonitoring.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mapping mapping;
    private static final Logger logger = LoggerFactory.getLogger(CropServiceImpl.class);

    @Override
    public void save(CropDTO cropDTO) {

        cropDTO.setCropCode(generateCropID());
        CropEntity entity = cropDAO.save(mapping.toCropsEntity(cropDTO));
        logger.info("Saved crop entity: {}", entity);
        System.out.println("entity = " + entity);
        if (entity.getCropCode() == null) {
            throw new DataPersistFailedException("Failed To Save");
        }
    }

    @Override
    @Transactional
    public void update(String id, CropDTO cropDTO) {
        Optional<CropEntity> cropEntity = cropDAO.findById(id);
        if (cropEntity.isPresent()) {
            cropEntity.get().setCropImage(cropDTO.getCropImage());
            cropEntity.get().setCropSeason(cropDTO.getCropSeason());
            cropEntity.get().setCategory(cropDTO.getCategory());
            cropEntity.get().setCropCommonName(cropDTO.getCropCommonName());
            cropEntity.get().setCropScientificName(cropDTO.getCropScientificName());
            cropEntity.get().setField(mapping.toFieldEntity(cropDTO.getFieldCode()));


        }else {
        throw new DataPersistFailedException("Failed To Update");
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
