package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.StaffResponse;
import lk.ijse.cropsMonitoring.dao.StaffDAO;
import lk.ijse.cropsMonitoring.dto.impl.StaffDTO;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import lk.ijse.cropsMonitoring.entity.StaffEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.StaffService;
import lk.ijse.cropsMonitoring.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private Mapping mapping;

    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);
    @Autowired
    private StaffDAO staffDAO;

    @Override
    public void save(StaffDTO staffDTO) {
        if (staffDTO == null) {
            throw new IllegalArgumentException("FieldDTO cannot be null");
        }
        staffDTO.setId(generateStaffID());
        StaffEntity entity = mapping.toStaffEntity(staffDTO);
        StaffEntity savedEntity = staffDAO.save(entity);
        logger.info("Saved field entity: {}", savedEntity);
        System.out.println("Saved entity = " + savedEntity);

        if (savedEntity == null || savedEntity.getId() == null) {
            throw new DataPersistFailedException("Failed to save the field entity");
        }
    }

    private String generateStaffID() {
        long staffCount = staffDAO.count();
        if (staffCount == 0) {
            return "S001";
        } else {

            String lastId = staffDAO.findLastStaffCode();
            if (lastId != null && lastId.startsWith("S")) {

                try {
                    int newId = Integer.parseInt(lastId.substring(1)) + 1;
                    return String.format("S%03d", newId);
                } catch (NumberFormatException e) {

                    logger.error("Failed to parse ID: {}", lastId, e);
                }
            }
            return "S001";
        }
    }


    @Override
    @Transactional
    public void update(String id, StaffDTO staffDTO) {
        Optional<StaffEntity> staffEntity = staffDAO.findById(id);
        if (staffEntity.isPresent()) {
            staffEntity.get().setFirstName(staffDTO.getFirstName());
            staffEntity.get().setLastName(staffDTO.getLastName());
           // staffEntity.get().setGender(staffDTO.getGender());
            staffEntity.get().setJoinedDate(staffDTO.getJoinedDate());
            staffEntity.get().setDob(staffDTO.getDob());
            staffEntity.get().setAddressLine01(staffDTO.getAddressLine01());
            staffEntity.get().setAddressLine02(staffDTO.getAddressLine02());
            staffEntity.get().setAddressLine03(staffDTO.getAddressLine03());
            staffEntity.get().setAddressLine04(staffDTO.getAddressLine04());
            staffEntity.get().setAddressLine05(staffDTO.getAddressLine05());
            staffEntity.get().setContactNo(staffDTO.getContactNo());
            staffEntity.get().setEmail(staffDTO.getEmail());
           // staffEntity.get().setRole(staffDTO.getRole());
            //staffEntity.get().setFields(staffDTO.getFieldCodes());
            //staffEntity.get().setVehicles(staffDTO.getVehicleCodes());



        }else {
            throw new DataPersistFailedException("Failed To Update");
        }
    }

    @Override

    public void delete(String id) {
        if (staffDAO.existsById(id)) {
            staffDAO.deleteById(id);
        } else {
            throw new DataPersistFailedException("Failed To Delete");
        }
    }

    @Override
    public StaffResponse getSelectedStaff(String id) {
        StaffEntity staffEntity = staffDAO.findById(id).orElse(null);
        if (staffEntity == null) {
            return mapping.toStaffDto(staffEntity);
        }else {
            throw new DataPersistFailedException("Failed To Get");
        }
    }

    @Override
    public List<StaffDTO> getAll() {
        return mapping.toStaffDtoList(staffDAO.findAll());
    }
}
