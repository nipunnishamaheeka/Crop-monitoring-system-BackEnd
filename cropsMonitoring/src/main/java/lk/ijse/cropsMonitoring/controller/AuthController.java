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
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Replace with frontend URL
public class AuthController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // **Sign-Up**
    @PostMapping(value = "signup", consumes = "application/json")
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody UserDTO user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            JwtAuthResponse jwtAuthResponse = authenticationService.signUp(user);
            if (jwtAuthResponse != null) {
                return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (AlreadyExistsException e) {
            logger.warn("User already exists with email: {}", user.getEmail());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to persist user with email: {}", user.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // **Sign-In with JWT Cookies**
    @PostMapping(value = "signin", consumes = "application/json")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignIn signIn, HttpServletResponse response) {
        JwtAuthResponse jwtAuthResponse = authenticationService.signIn(signIn);

        if (jwtAuthResponse != null) {
            // Set JWT as HTTP-Only cookies
            ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", jwtAuthResponse.getToken())
                    .httpOnly(true)
                    .secure(false) // Set true in production (HTTPS)
                    .path("/")
                    .maxAge(24 * 60 * 60) // 1 day
                    .build();

            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwtAuthResponse.getToken())
                    .httpOnly(true)
                    .secure(false) // Set true in production (HTTPS)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60) // 7 days
                    .build();

            response.addHeader("Set-Cookie", accessTokenCookie.toString());
            response.addHeader("Set-Cookie", refreshTokenCookie.toString());

            return ResponseEntity.ok(jwtAuthResponse);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // **Refresh Token**
    @PostMapping("refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(
            @CookieValue("refreshToken") String refreshToken, // Extract from cookie
            HttpServletResponse response
    ) {
        JwtAuthResponse jwtAuthResponse = authenticationService.refreshToken(refreshToken);

        if (jwtAuthResponse != null) {
            // Update Access Token Cookie
            ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", jwtAuthResponse.getToken())
                    .httpOnly(true)
                    .secure(false) // Set true in production (HTTPS)
                    .path("/")
                    .maxAge(24 * 60 * 60) // 1 day
                    .build();

            response.addHeader("Set-Cookie", accessTokenCookie.toString());
            return ResponseEntity.ok(jwtAuthResponse);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // **Logout**
    @PostMapping("logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Expire both cookies
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false) // Set true in production (HTTPS)
                .path("/")
                .maxAge(0) // Expire immediately
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // Set true in production (HTTPS)
                .path("/")
                .maxAge(0) // Expire immediately
                .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.noContent().build();
    }
}
