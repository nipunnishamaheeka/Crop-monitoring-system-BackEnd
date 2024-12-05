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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Slf4j
public class EquipmentController {

    private final EquipmentService  equipmentService;

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody EquipmentDTO equipmentDTO) {
        if (equipmentDTO == null) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        try {
            equipmentService.save(equipmentDTO);
            log.info("saved: {}", equipmentDTO);
            return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persistence failed: {}", e.getMessage());
            return new ResponseEntity<>("Failed to save", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
    @PutMapping(value = "/{equipmentId}", params = {"staffId", "fieldCode"})
    public ResponseEntity<?> updateEquipments(
            @PathVariable("equipmentId") String equipmentId,
            @RequestBody EquipmentDTO equipmentDTO,
            @RequestParam("staffId") String staffId,
            @RequestParam("fieldCode") String fieldCode) {

        log.info("Received request to update equipment: equipmentId={}, staffId={}, fieldCode={}, equipmentDTO={}",
                equipmentId, staffId, fieldCode, equipmentDTO);

        try {
            // Perform the update operation
            equipmentService.update(equipmentDTO, staffId, fieldCode, equipmentId);

            log.info("Successfully updated equipment with ID: {}", equipmentId);
            return ResponseEntity.ok("Equipment updated successfully");
        } catch (DataPersistFailedException e) {
            log.error("Data persistence error while updating equipment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Data persistence error: " + e.getMessage());
        } catch (NotFoundException e) {
            log.error("Not found error while updating equipment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Not found error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating equipment: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
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

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATIVE')")
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
