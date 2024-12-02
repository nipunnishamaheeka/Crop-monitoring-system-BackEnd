package lk.ijse.cropsMonitoring.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lk.ijse.cropsMonitoring.dto.impl.UserDTO;
import lk.ijse.cropsMonitoring.exception.AlreadyExistsException;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.jwtmodels.JwtAuthResponse;
import lk.ijse.cropsMonitoring.jwtmodels.SignIn;
import lk.ijse.cropsMonitoring.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*", allowedHeaders = "*") // Replace with frontend URL
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // **Sign-Up**
    @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody UserDTO user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            JwtAuthResponse jwtAuthResponse = authenticationService.signUp(user);
            if (jwtAuthResponse != null){
                return new ResponseEntity<>(jwtAuthResponse,HttpStatus.CREATED);
            } else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (AlreadyExistsException e) {
            logger.warn("User already exists with email: {}", user.getEmail());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to persist user with email: {}", user.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "signin")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignIn signIn) {
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }
    @PostMapping("refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken (@RequestParam ("refreshToken") String refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}
