package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dao.CropDAO;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.CropService;
import lk.ijse.cropsMonitoring.util.AppUtil;
import lk.ijse.cropsMonitoring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void save(CropDTO cropDTO) {
        cropDTO.setCropCode(AppUtil.createCropId());
        CropEntity entity = cropDAO.save(mapping.toCropsEntity(cropDTO));
        if (entity.getCropCode() == null) {
            throw new DataPersistFailedException("Failed To Save");
        }
    }

    @Override
    public void update(String id, CropDTO cropDTO) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public CropResponse getSelectedCrops(String id) {
        return null;
    }

    @Override
    public List<CropDTO> getAll() {
        return List.of();
    }
}
