package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.EquipmentErrorResponse;
import lk.ijse.cropsMonitoring.customObj.EquipmentResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleErrorResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.EquipmentDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/equipment" , method = RequestMethod.OPTIONS)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.1:5500")
public class EquipmentController {

    @Autowired
    private final EquipmentService  equipmentService;

    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> save(@RequestBody EquipmentDTO equipmentDTO) {
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
    public ResponseEntity<String> updateCrops(@PathVariable("equipmentId") String id, @RequestBody EquipmentDTO equipmentDTO) {
        try {
            equipmentService.update(id, equipmentDTO);

            return new ResponseEntity<>("Equipment updated successfully", HttpStatus.OK);

//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>("Equipment not found", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getEquipment(@PathVariable("equipmentId") String equipmentId) {
        EquipmentResponse equipmentResponse = equipmentService.getSelectedEquipment(equipmentId);
        return equipmentResponse == null? new EquipmentErrorResponse() :equipmentResponse;
    }
    @GetMapping(value = "allEquipments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAll() {
        return equipmentService.getAll();
    }

    @DeleteMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> deleteV(@PathVariable("equipmentId") String id) {
        System.out.println("Deleting  with ID: " + id);
        try {
            equipmentService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
