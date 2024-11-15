package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.FieldErrorResponse;
import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.FieldService;
import lk.ijse.cropsMonitoring.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class FieldController {

    @Autowired
    private final FieldService fieldService;
    private static final Logger logger = Logger.getLogger(FieldController.class.getName());

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> save(
            @RequestParam("name") String name,
            @RequestParam("location") String location,
            @RequestParam("size_of_Field") double sizeOfField,
            @RequestParam("cropCodes") List<String> crop_code,
            @RequestParam("staffIds") List<String> staffIds,
            @RequestParam("fieldImage1") MultipartFile fieldImage1,
            @RequestParam("fieldImage2") MultipartFile fieldImage2) {

            try {
            byte[] imageByteCollection1 = fieldImage1.getBytes();
            byte[] imageByteCollection2 = fieldImage2.getBytes();
            String base64FieldImage1 = AppUtil.toBase64FieldImage(imageByteCollection1);
            String base64FieldImage2 = AppUtil.toBase64FieldImage(imageByteCollection2);

    var fieldDTO = new FieldDTO();
            fieldDTO.setName(name);
            fieldDTO.setLocation(location);
            fieldDTO.setSize_of_Field(sizeOfField);
            fieldDTO.setCropCodes(crop_code);
            fieldDTO.setStaffIds(staffIds);
            fieldDTO.setFieldImage1(base64FieldImage1);
            fieldDTO.setFieldImage2(base64FieldImage2);
            fieldService.save(fieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

}
    }
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> save(
//            @RequestParam("name") String name,
//            @RequestParam("location") String location,
//            @RequestParam("size_of_Field") double sizeOfField,
//            @RequestParam("cropCodes") List<String> cropCodes,
//            @RequestParam("staffIds") List<String> staffIds,
//            @RequestParam("fieldImage1") MultipartFile fieldImage1,
//            @RequestParam("fieldImage2") MultipartFile fieldImage2) {
//
//        try {
//            // Validate required parameters
//            if (name == null || location == null || cropCodes == null || staffIds == null || fieldImage1.isEmpty() || fieldImage2.isEmpty()) {
//                return new ResponseEntity<>("Missing required fields or images", HttpStatus.BAD_REQUEST);
//            }
//
//            // Convert images to Base64 strings
//            String encodedImage1 = Base64.getEncoder().encodeToString(fieldImage1.getBytes());
//            String encodedImage2 = Base64.getEncoder().encodeToString(fieldImage2.getBytes());
//
//            // Create FieldDTO without code (it will be generated in the service layer)
//            FieldDTO fieldDTO = new FieldDTO();
//            fieldDTO.setName(name);
//            fieldDTO.setLocation(location);
//            fieldDTO.setSize_of_Field(sizeOfField);
//            fieldDTO.setCropCodes(cropCodes);
//            fieldDTO.setStaffIds(staffIds);
//            fieldDTO.setFieldImage1(encodedImage1);
//            fieldDTO.setFieldImage2(encodedImage2);
//
//            // Log the field data creation attempt
//            logger.info("Saving field: " + fieldDTO);
//
//            // Save the field data
//            fieldService.save(fieldDTO);
//
//            return new ResponseEntity<>("Field created successfully", HttpStatus.CREATED);
//
//        } catch (IOException e) {
//            logger.severe("Error processing the images: " + e.getMessage());
//            return new ResponseEntity<>("Failed to process images", HttpStatus.BAD_REQUEST);
//        } catch (DataPersistFailedException e) {
//            logger.severe("Data persistence failed: " + e.getMessage());
//            return new ResponseEntity<>("Failed to save field data", HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            logger.severe("Unexpected error occurred: " + e.getMessage());
//            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @PutMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateField(@PathVariable("code") String id, @RequestBody FieldDTO fieldDTO) {
        try {
            fieldService.update(id, fieldDTO);
            return new ResponseEntity<>("Field updated successfully", HttpStatus.OK);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>("Field not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getField(@PathVariable("code") String code) {
        FieldResponse field = fieldService.getSelectedField(code);
        return field == null ? new FieldErrorResponse() : field;
    }
    @GetMapping(value = "allFields", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields() {
        return fieldService.getAll();
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<Void> deleteField(@PathVariable("code") String id) {
        try {
            fieldService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
