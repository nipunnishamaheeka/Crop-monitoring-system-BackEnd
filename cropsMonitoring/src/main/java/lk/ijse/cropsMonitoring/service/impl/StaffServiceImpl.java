package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.StaffErrorResponse;
import lk.ijse.cropsMonitoring.customObj.StaffResponse;
import lk.ijse.cropsMonitoring.dao.StaffDAO;
import lk.ijse.cropsMonitoring.dto.impl.StaffDTO;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import lk.ijse.cropsMonitoring.entity.StaffEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.StaffService;
import lk.ijse.cropsMonitoring.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final Mapping mapping;

    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);
    private final StaffDAO staffDAO;

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
    public void update(String id, StaffDTO staffDTO) {
        Optional<StaffEntity> staff = staffDAO.findById(id);
        if (staff.isPresent()){
            staffDTO.setId(id);
            StaffEntity save = staffDAO.save(mapping.toStaffEntity(staffDTO));
            if (save == null){
                throw new DataPersistFailedException("Staff update failed");
            }
        }else {
            throw new NotFoundException("Staff not found");
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
        Optional<StaffEntity> staff = staffDAO.findById(id);
        if (staff.isPresent()){
            return mapping.toStaffDto(staff.get());
        }else {
            return new StaffErrorResponse(404, "Staff not found");
        }
    }

    @Override
    public List<StaffDTO> getAll() {
        return mapping.toStaffDtoList(staffDAO.findAll());
    }
}
