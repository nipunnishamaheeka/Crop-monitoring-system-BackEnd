package lk.ijse.cropsMonitoring.controller;

import jakarta.validation.Valid;
import lk.ijse.cropsMonitoring.dto.impl.UserDTO;
import lk.ijse.cropsMonitoring.exception.AlreadyExistsException;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.UserService;
import lk.ijse.cropsMonitoring.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Slf4j
public class UserController {
    private final UserService userService;

    private final Mapping mapping;
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
        log.info("Attempting to save user with email: {}", user.getEmail());
        try {
            userService.saveUser(user);
            log.info("User saved successfully with email: {}", user.getEmail());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (AlreadyExistsException e) {
            log.warn("User already exists with email: {}", user.getEmail());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            log.error("Failed to persist user with email: {}", user.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        log.info("Fetching user with email: {}", email);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }
    @PatchMapping(value = "/{email}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO user, @PathVariable("email") String email) {
        log.info("Attempting to update user with email: {}", email);
        try {
            userService.updateUser(user, email);
            log.info("User updated successfully with email: {}", email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            log.warn("User not found with email: {}", email);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
