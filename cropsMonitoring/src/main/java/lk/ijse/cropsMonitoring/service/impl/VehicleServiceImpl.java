package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dao.VehicleManagementDAO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;
import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.entity.VehicleManagementEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.VehicleService;
import lk.ijse.cropsMonitoring.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleManagementDAO vehicleManagementDAO;
    @Autowired
    private Mapping mapping;

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
    @Override
    public void save(VehicleManagementDTO vehicleManagementDTO) {
        vehicleManagementDTO.setVehicleCode(generateVehicleID());
        VehicleManagementEntity entity = vehicleManagementDAO.save(mapping.toVehicleEntity(vehicleManagementDTO));
        logger.info("Saved entity: {}", entity);
        System.out.println("entity = " + entity);
        if (entity.getVehicleCode() == null) {
            throw new DataPersistFailedException("Failed To Save");
        }
    }

    @Override
    @Transactional
    public void update(String id, VehicleManagementDTO vehicleManagementDTO) {
//        Optional<VehicleManagementEntity> vehicleManagementEntity = vehicleManagementDAO.findById(id);
//        if (vehicleManagementEntity.isPresent()) {
//            vehicleManagementEntity.get().setVehicleCategory(vehicleManagementDTO.getVehicleCategory());
//            vehicleManagementEntity.get().setLicensePlateNo(vehicleManagementDTO.getLicensePlateNo());
//            vehicleManagementEntity.get().setFuelType(vehicleManagementDTO.getFuelType());
//            vehicleManagementEntity.get().setStatus(vehicleManagementDTO.getStatus());
//            vehicleManagementEntity.get().setRemarks(vehicleManagementDTO.getRemarks());
//
//
//        }else {
//            throw new DataPersistFailedException("Failed To Update");
//        }
    }

    @Override
    public void delete(String id) {
        if (vehicleManagementDAO.existsById(id)) {
            vehicleManagementDAO.deleteById(id);
        } else {
            throw new DataPersistFailedException("Failed To Delete");
        }
    }

    @Override
    public VehicleResponse getSelectedVehicle(String id) {

        VehicleManagementEntity vehicleManagementEntity = vehicleManagementDAO.findById(id).orElse(null);
        if (vehicleManagementEntity == null) {
            return mapping.toVehicleDto(vehicleManagementEntity);
        }else {
            throw new DataPersistFailedException("Failed To Get");
        }
    }

    @Override
    public List<VehicleManagementDTO> getAll() {
        return mapping.toVehicleDtoList(vehicleManagementDAO.findAll());
    }

    private String generateVehicleID() {
        long vehicleCount = vehicleManagementDAO.count();
        if (vehicleCount == 0) {
            return "V001";
        } else {

            String lastId = vehicleManagementDAO.findLastVehicleCode();
            if (lastId != null && lastId.startsWith("V")) {

                try {
                    int newId = Integer.parseInt(lastId.substring(1)) + 1;
                    return String.format("V%03d", newId);
                } catch (NumberFormatException e) {

                    logger.error("Failed to parse ID: {}", lastId, e);
                }
            }
            return "V001";
        }
    }
}
