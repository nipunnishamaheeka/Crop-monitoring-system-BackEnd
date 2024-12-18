package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.CropErrorResponse;
import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.CropService;
import lk.ijse.cropsMonitoring.service.impl.CropServiceImpl;
import lk.ijse.cropsMonitoring.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/crops", method = RequestMethod.OPTIONS)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Slf4j

public class CropController {

    private final CropService cropService;

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PostMapping
    public ResponseEntity<String> saveCrops(

            @RequestPart("cropName") String cropName,
            @RequestPart("cropType") String cropCategory,
            @RequestPart("cropSeason") String cropSeason,
            @RequestPart("cropScientificName") String cropScientificName,
            @RequestParam("cropImage") MultipartFile cropImage,
            @RequestParam("fieldCode") String fieldCode
    ) {


        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCommonName(cropName);
        cropDTO.setCategory(cropCategory);
        cropDTO.setCropSeason(cropSeason);
        cropDTO.setCropScientificName(cropScientificName);
        cropDTO.setCropImage(AppUtil.toBase64(cropImage));
        cropDTO.setFieldCode(fieldCode);
        System.out.println("cropDTO ekata awa= " + cropDTO);

        try {
            log.info("Request received to save a new crop: {}", cropDTO);
            cropService.save(cropDTO,fieldCode);
            System.out.println("cropDTO controller= " + cropDTO);
            log.info("Crop saved: {}", cropDTO);
            return new ResponseEntity<>("Crop created successfully", HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persistence failed: {}", e.getMessage());
            return new ResponseEntity<>("Failed to save crop", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @PutMapping(value = "/{crop_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCrops(
            @RequestPart("cropName") String cropName,
            @RequestPart("cropType") String cropCategory,
            @RequestPart("cropSeason") String cropSeason,
            @RequestPart("cropScientificName") String cropScientificName,
            @RequestParam(value = "fieldCode", required = true) String fieldCode,
            @RequestParam("cropImage") MultipartFile cropImage,
            @PathVariable String crop_code
    ) {
        // Validate required fields
        if (fieldCode == null || fieldCode.trim().isEmpty()) {
            log.warn("FieldCode is missing or invalid.");
            return new ResponseEntity<>("FieldCode is required and must not be null or empty.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Populate CropDTO with provided data
            CropDTO cropDTO = new CropDTO();
            cropDTO.setCropCommonName(cropName);
            cropDTO.setCategory(cropCategory);
            cropDTO.setCropSeason(cropSeason);
            cropDTO.setCropScientificName(cropScientificName);
            cropDTO.setCropImage(AppUtil.toBase64(cropImage));
            cropDTO.setFieldCode(fieldCode);

            log.info("Request received to update crop: {}", cropDTO);

            // Perform the update operation
            cropService.update(crop_code, cropDTO, fieldCode);

            log.info("Crop updated successfully: {}", cropDTO);
            return new ResponseEntity<>("Crop updated successfully", HttpStatus.OK);
        } catch (DataPersistFailedException e) {
            log.error("Failed to update crop: {}", e.getMessage());
            return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating crop: {}", e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{crop_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getCrop(@PathVariable String crop_code) {
        CropResponse crop = cropService.getSelectedCrops(crop_code);
        return crop==null? new CropErrorResponse() :crop;
    }

    @GetMapping(value = "allCrops", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllVehicles() {
        return cropService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_SCIENTIST')")
    @DeleteMapping(value = "/{crop_code}")
    public ResponseEntity<Void> deleteCrops(@PathVariable("crop_code") String id) {
        System.out.println("Deleting crop with ID: " + id);
        try {
            cropService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
