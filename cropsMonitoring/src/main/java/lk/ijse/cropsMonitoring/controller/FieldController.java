package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.FieldErrorResponse;
import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleErrorResponse;
import lk.ijse.cropsMonitoring.customObj.VehicleResponse;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.dto.impl.VehicleManagementDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/v1/field", method = RequestMethod.OPTIONS)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class FieldController {

    @Autowired
    private final FieldService fieldService;
    private static final Logger logger = Logger.getLogger(FieldController.class.getName());

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> save(@RequestBody FieldDTO fieldDTO) {
        if (fieldDTO == null) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        try {
            fieldService.save(fieldDTO);
//            logger.info("saved: {}", fieldDTO);
            return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
//            logger.error("Data persistence failed: {}", e.getMessage());
            return new ResponseEntity<>("Failed to save", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
//            logger.error("Unexpected error occurred: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCrops(@PathVariable("code") String id, @RequestBody FieldDTO fieldDTO) {
        try {
            fieldService.update(id, fieldDTO);

            return new ResponseEntity<>(" updated successfully", HttpStatus.OK);

//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(" not found", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getVehicle(@PathVariable("code") String code) {
        FieldResponse field = fieldService.getSelectedField(code);
        return field == null? new FieldErrorResponse() :field;
    }
    @GetMapping(value = "allFields", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAll() {
        return fieldService.getAll();
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<Void> delete(@PathVariable("code") String id) {
        System.out.println("Deleting field with ID: " + id);
        try {
            fieldService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
