package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.EquipmentResponse;
import lk.ijse.cropsMonitoring.dao.EquipmentDAO;
import lk.ijse.cropsMonitoring.dto.impl.EquipmentDTO;
import lk.ijse.cropsMonitoring.entity.EquipmentEntity;
import lk.ijse.cropsMonitoring.entity.VehicleManagementEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.EquipmentService;
import lk.ijse.cropsMonitoring.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentDAO equipmentDAO;
    @Autowired
    private Mapping mapping;

    private static final Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Override
    public void save(EquipmentDTO equipmentDTO) {
        equipmentDTO.setEquipmentId(generateID());
        EquipmentEntity entity = equipmentDAO.save(mapping.toEquipmentEntity(equipmentDTO));
        logger.info("Saved entity: {}", entity);
        System.out.println("entity = " + entity);
        if (entity.getEquipmentId() == null) {
            throw new DataPersistFailedException("Failed To Save");
        }
    }

    @Override
    @Transactional
    public void update(String id, EquipmentDTO equipmentDTO) {
//        Optional<EquipmentEntity> equipmentEntity = equipmentDAO.findById(id);
//        if (equipmentEntity.isPresent()) {
//            equipmentEntity.get().setName(equipmentDTO.getName());
//            equipmentEntity.get().setType(equipmentDTO.getType());
//            equipmentEntity.get().setStatus(equipmentDTO.getStatus());
////            equipmentEntity.get().(vehicleManagementDTO.getStatus());
////            equipmentEntity.get().setRemarks(vehicleManagementDTO.getRemarks());
//
//
//        }else {
//            throw new DataPersistFailedException("Failed To Update");
//        }

    }

    @Override
    public void delete(String id) {
        if(equipmentDAO.existsById(id)){
            equipmentDAO.deleteById(id);
        }else {
            throw new DataPersistFailedException("Failed To Delete");
        }
    }

    @Override
    public EquipmentResponse getSelectedEquipment(String id) {
        EquipmentEntity equipmentEntity = equipmentDAO.findById(id).orElse(null);
        if (equipmentEntity == null) {
            return mapping.toEquipmentDto(equipmentEntity);
        }
        throw new DataPersistFailedException("Failed To Get");
    }

    @Override
    public List<EquipmentDTO> getAll() {
        return mapping.toEquipmentDtoList(equipmentDAO.findAll());
    }

    private String generateID() {
        long EqCount = equipmentDAO.count();
        if (EqCount == 0) {
            return "E001";
        } else {

            String lastId = equipmentDAO.findLastEquipmentCode();
            if (lastId != null && lastId.startsWith("E")) {

                try {
                    int newId = Integer.parseInt(lastId.substring(1)) + 1;
                    return String.format("E%03d", newId);
                } catch (NumberFormatException e) {

                    logger.error("Failed to parse ID: {}", lastId, e);
                }
            }
            return "E001";
        }
    }
}
