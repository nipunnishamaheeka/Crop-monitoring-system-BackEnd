package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.FieldErrorResponse;
import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.FieldService;
import lk.ijse.cropsMonitoring.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Slf4j
public class FieldController {
    private final FieldService fieldService;



    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> save(
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocationX") int fieldLocationX,
            @RequestParam("fieldSize") double fieldSize,
            @RequestParam("image1") MultipartFile image1,
            @RequestParam("image2") MultipartFile image2,
//            @RequestParam("staffId") List<String> staffId,
            @RequestParam("fieldLocationY") int fieldLocationY ){

        log.info("y"+fieldLocationY +"x"+fieldLocationX);
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldName(fieldName);
        fieldDTO.setFieldLocation(new Point(fieldLocationX, fieldLocationY));
        fieldDTO.setFieldSize(fieldSize);
//        fieldDTO.setStaffId(staffId);
        fieldDTO.setImage1(AppUtil.toBase64(image1));
        fieldDTO.setImage2(AppUtil.toBase64(image2));

        log.info("Request received to save a new field: {}", fieldDTO);
        try {
            fieldService.save(fieldDTO);
            log.info("Field saved successfully: {}", fieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Failed to save field: {}", fieldDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal server error while saving field: {}", fieldDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PutMapping(value = "/{fieldCode}",params = "staffIds")
    public ResponseEntity<?> updateField(
            @PathVariable("fieldCode") String fieldCode,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocationX") int fieldLocationX,
            @RequestParam("fieldSize") double fieldSize,
            @RequestParam("image1") MultipartFile image1,
            @RequestParam("image2") MultipartFile image2,
            @RequestParam("fieldLocationY") int fieldLocationY,
            @RequestParam("staffIds") List<String> staffIds) {

        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldCode(fieldCode);
        fieldDTO.setFieldName(fieldName);
        fieldDTO.setFieldLocation(new Point(fieldLocationX, fieldLocationY));
        fieldDTO.setFieldSize(fieldSize);
        fieldDTO.setImage1(AppUtil.toBase64(image1));
        fieldDTO.setImage2(AppUtil.toBase64(image2));
        log.info("Request received to update field with staff IDs {}: {}", staffIds, fieldDTO);
        try {
            fieldService.update(fieldDTO,staffIds);
            log.info("Field updated successfully: {}", fieldDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error("Field not found for update: {}", fieldDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            log.error("Failed to update field: {}", fieldDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("Internal server error while updating field: {}", fieldDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getField(@PathVariable String fieldCode) {
        try {
            var field = fieldService.getSelectedField(fieldCode);
            log.info("Field retrieved successfully: {}", field);
            return new ResponseEntity<>(field, HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error("Field not found with code: {}", fieldCode, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal server error while retrieving field with code: {}", fieldCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "allFields", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields() {
        return fieldService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
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
