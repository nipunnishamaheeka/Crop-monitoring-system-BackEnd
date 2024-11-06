package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.CropErrorResponse;
import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.CropService;
import lk.ijse.cropsMonitoring.service.impl.CropServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crops")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CropController {
    @Autowired
    private final CropService cropService;

    private static final Logger logger = LoggerFactory.getLogger(CropController.class);



    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> saveCrops(@RequestBody CropDTO cropDTO) {
        if (cropDTO == null) {
            return new ResponseEntity<>("Invalid crop data", HttpStatus.BAD_REQUEST);
        }
        try {
            cropService.save(cropDTO);
            logger.info("Crop saved: {}", cropDTO);
            return new ResponseEntity<>("Crop created successfully", HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            logger.error("Data persistence failed: {}", e.getMessage());
            return new ResponseEntity<>("Failed to save crop", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{crop_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCrops(@PathVariable("crop_code") String id, @RequestBody CropDTO cropDTO) {
        try {
            cropService.update(id, cropDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{crop_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getNote(@PathVariable("crop_code") String crop_code) {
        CropResponse note = cropService.getSelectedCrops(crop_code);
        return note==null? new CropErrorResponse() :note;
    }

    @GetMapping(value = "allCrops", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllNotes() {
        return cropService.getAll();
    }

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
