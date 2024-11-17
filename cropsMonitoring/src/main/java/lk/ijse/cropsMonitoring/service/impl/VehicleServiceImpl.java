package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleErrorResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dao.StaffDAO;
import lk.ijse.cropsMonitoring.dao.VehicleManagementDAO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;
import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.entity.StaffEntity;
import lk.ijse.cropsMonitoring.entity.VehicleManagementEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.VehicleService;
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
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleManagementDAO vehicleManagementDAO;
    private final StaffDAO staffDAO;
    private final Mapping mapping;

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
    public void update(VehicleManagementDTO vehicleDTO, String staffId , String vehicleCode) {
        VehicleManagementEntity vehicle = vehicleManagementDAO.findById(vehicleCode)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));

        StaffEntity staff = null;
        if (!staffId.equals("N/A")) {

            staff = staffDAO.findById(staffId)
                    .orElseThrow(() -> new NotFoundException("Staff not found"));
            vehicle.setStaff(staff);
        }

        vehicle.setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
        vehicle.setVehicleCategory(vehicleDTO.getVehicleCategory());
        vehicle.setFuelType(vehicleDTO.getFuelType());
        vehicle.setStatus(vehicleDTO.getStatus());
        vehicle.setRemarks(vehicleDTO.getRemarks());

        if (staff != null) {
            vehicle.setStaff(staff);
        } else {
            vehicle.setStaff(null);
        }

        vehicleManagementDAO.save(vehicle);
    }

    @Override
    public void delete(String vehicleCode) {
        if (vehicleManagementDAO.existsById(vehicleCode)) {
            vehicleManagementDAO.deleteById(vehicleCode);
        } else {
            throw new DataPersistFailedException("Failed To Delete");
        }
    }

    @Override
    public VehicleResponse getSelectedVehicle(String vehicleCode) {
        Optional<VehicleManagementEntity> byId = vehicleManagementDAO.findById(vehicleCode);
        if (byId.isPresent()){
            return mapping.toVehicleDto(byId.get());
        }else {
            return new VehicleErrorResponse("40" , "vehicle not found");
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
