package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.FieldErrorResponse;
import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.customObj.StaffErrorResponse;
import lk.ijse.cropsMonitoring.customObj.StaffResponse;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.dto.impl.StaffDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Slf4j
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody StaffDTO staffDTO) {
        if (staffDTO == null) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        try {
            staffService.save(staffDTO);
            log.info("Saved staff: " + staffDTO);
            return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persistence failed: " + e.getMessage());

            return new ResponseEntity<>("Failed to save", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{staff_member_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStaff(@PathVariable("staff_member_id") String id, @RequestBody StaffDTO staffDTO) {
       log.info("Updating staff with ID: " + staffDTO);
        try {
            staffService.update(id, staffDTO);
            log.info("Updated staff successfully: " + staffDTO.getId());
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffResponse getStaff(@PathVariable("id") String id) {
        StaffResponse staff = staffService.getSelectedStaff(id);
        return staff == null ? new StaffErrorResponse() : staff;
    }

    @GetMapping(value = "allStaff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAll() {
        return staffService.getAll();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        log.info("Deleting staff with ID: " + id);
        try {
            staffService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
