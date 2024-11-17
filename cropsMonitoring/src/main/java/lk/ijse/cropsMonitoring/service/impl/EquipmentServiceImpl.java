package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.EquipmentErrorResponse;
import lk.ijse.cropsMonitoring.customObj.EquipmentResponse;
import lk.ijse.cropsMonitoring.dao.EquipmentDAO;
import lk.ijse.cropsMonitoring.dao.FieldDAO;
import lk.ijse.cropsMonitoring.dao.StaffDAO;
import lk.ijse.cropsMonitoring.dto.impl.EquipmentDTO;
import lk.ijse.cropsMonitoring.entity.EquipmentEntity;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import lk.ijse.cropsMonitoring.entity.StaffEntity;
import lk.ijse.cropsMonitoring.entity.VehicleManagementEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.EquipmentService;
import lk.ijse.cropsMonitoring.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final StaffDAO staffDAO;
    private final FieldDAO fieldDAO;
    private final EquipmentDAO equipmentDAO;
    private final Mapping mapping;

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
    public void update(EquipmentDTO equipmentDTO,String staffId,String fieldCode,String equipmentId) {

        EquipmentEntity equipmentEntity = equipmentDAO.findById(equipmentId).orElse(null);

        if (equipmentEntity != null) {
            equipmentEntity = mapping.toEquipmentEntity(equipmentDTO);
            equipmentEntity.setEquipmentId(equipmentId);
            if (staffId.equals("N/A")) {
                equipmentEntity.setStaff(null);
            } else {
                Optional<StaffEntity> optionalStaffEntity = staffDAO.findById(staffId);
                if (optionalStaffEntity.isPresent()) {
                    StaffEntity staffEntity = optionalStaffEntity.get();
                    equipmentEntity.setStaff(staffEntity);
                } else {
                    throw new NotFoundException("Staff not found");
                }
            }

            if (fieldCode.equals("N/A")) {
                equipmentEntity.setField(null);
            } else {
                Optional<FieldEntity> optionalFieldEntity = fieldDAO.findById(fieldCode);
                if (optionalFieldEntity.isPresent()) {
                    FieldEntity fieldEntity = optionalFieldEntity.get();
                    equipmentEntity.setField(fieldEntity);
                } else {
                    throw new DataPersistFailedException("Failed To Update");
                }
            }
        }
        if (equipmentEntity != null){
            EquipmentEntity save = equipmentDAO.save(equipmentEntity);
            if (save == null) {
                throw new DataPersistFailedException("Equipment update failed");
            }
        }else {
            throw new NotFoundException("Equipment not found");
        }
    }

    @Override
    public void delete(String equipmentId) {
        if(equipmentDAO.existsById(equipmentId)){
            equipmentDAO.deleteById(equipmentId);
        }else {
            throw new DataPersistFailedException("Failed To Delete");
        }
    }

    @Override
    public EquipmentResponse getSelectedEquipment(String equipmentId) {
        Optional<EquipmentEntity> equipment = equipmentDAO.findById(equipmentId);
        if (equipment.isPresent()){
            return mapping.toEquipmentDto(equipment.get());
        }else {
            return new EquipmentErrorResponse("Equipment not found", "404");
        }
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
