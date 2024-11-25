package lk.ijse.cropsMonitoring.service;

import lk.ijse.cropsMonitoring.dto.impl.UserDTO;
import lk.ijse.cropsMonitoring.jwtmodels.JwtAuthResponse;
import lk.ijse.cropsMonitoring.jwtmodels.SignIn;

public interface AuthenticationService {
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse signUp(UserDTO signUp);
    JwtAuthResponse refreshToken(String accessToken);
}
