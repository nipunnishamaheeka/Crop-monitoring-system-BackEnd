package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.CropErrorResponse;
import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleErrorResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/vehicle", method = RequestMethod.OPTIONS)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Slf4j
public class VehicleManagementController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody VehicleManagementDTO vehicleManagementDTO) {
        if (vehicleManagementDTO == null) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        try {
            vehicleService.save(vehicleManagementDTO);
            log.info("saved: {}", vehicleManagementDTO);
            return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persistence failed: {}", e.getMessage());
            return new ResponseEntity<>("Failed to save", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{vehicleCode}", params = "staffId")
    public ResponseEntity<?> updateCrops(@RequestBody VehicleManagementDTO vehicleDTO , @RequestParam("staffId") String staffId , @PathVariable("vehicleCode") String vehicleCode) {
        try {
            log.info("Attempting to update vehicle: {}", vehicleDTO);
            vehicleService.update(vehicleDTO, staffId, vehicleCode);
            log.info("Vehicle updated successfully: {}", vehicleDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error("Failed to update vehicle: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            log.error("Failed to persist vehicle data: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("An error occurred while updating the vehicle: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{vehicleCode}")
    public VehicleResponse getVehicle(@PathVariable("vehicleCode") String v_code) {
       VehicleResponse vehicle = vehicleService.getSelectedVehicle(v_code);
        return vehicle == null? new VehicleErrorResponse() :vehicle;
    }
    @GetMapping(value = "allVehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<?> getAllVehicles() {
        return vehicleService.getAll();
    }

    @DeleteMapping(value = "/{vehicleCode}")
    public ResponseEntity<?> deleteVehicles(@PathVariable("vehicleCode") String vehicleCode) {
        System.out.println("Deleting vehicle with ID: " + vehicleCode);
        try {
            vehicleService.delete(vehicleCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
