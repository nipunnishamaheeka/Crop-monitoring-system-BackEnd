package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.customObj.CropErrorResponse;
import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.service.CropService;
import lombok.RequiredArgsConstructor;
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
public class CropController {
    @Autowired
    private final CropService cropService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrops(@RequestBody CropDTO cropDTO) {
        if (cropDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try{
                cropService.save(cropDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
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
