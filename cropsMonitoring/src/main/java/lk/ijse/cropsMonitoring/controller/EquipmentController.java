package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.EquipmentErrorResponse;
import lk.ijse.cropsMonitoring.customObj.EquipmentResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleErrorResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.EquipmentDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class EquipmentController {

    private final EquipmentService  equipmentService;
    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> save(@RequestBody EquipmentDTO equipmentDTO) {
        if (equipmentDTO == null) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        try {
            equipmentService.save(equipmentDTO);
            logger.info("saved: {}", equipmentDTO);
            return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            logger.error("Data persistence failed: {}", e.getMessage());
            return new ResponseEntity<>("Failed to save", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCrops(

            @PathVariable("equipmentId") String equipmentId,
            @RequestBody EquipmentDTO equipmentDTO,
            @RequestParam("staffIds") String staffId,
            @RequestParam("fieldCode") String fieldCode) {
        logger.info("Received request to update equipment: staffId={}, fieldCode={}, equipmentDTO={}", staffId, fieldCode, equipmentDTO);

        try {
            equipmentService.update(equipmentDTO, staffId, fieldCode , equipmentId);
            logger.info("Successfully updated equipment with ID: {}", equipmentDTO.getEquipmentId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to update equipment due to data persistence issue: {}", e.getMessage());
            return new ResponseEntity<>("Data persistence issue: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            logger.error("Failed to update equipment due to not found issue: {}", e.getMessage());
            return new ResponseEntity<>("Not found issue: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating equipment: {}", e.getMessage(), e);
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getEquipment(@PathVariable String equipmentId) {
        EquipmentResponse equipmentResponse = equipmentService.getSelectedEquipment(equipmentId);
        return equipmentResponse == null? new EquipmentErrorResponse() :equipmentResponse;
    }
    @GetMapping(value = "allEquipments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAll() {
        return equipmentService.getAll();
    }

    @DeleteMapping(value = "/{equipmentId}")
    public ResponseEntity<?> deleteV(@PathVariable String equipmentId) {
        System.out.println("Deleting  with ID: " + equipmentId);
        try {
            equipmentService.delete(equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
