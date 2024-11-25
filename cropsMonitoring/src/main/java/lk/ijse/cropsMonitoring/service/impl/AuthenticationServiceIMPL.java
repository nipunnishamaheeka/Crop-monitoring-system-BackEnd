package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.dao.UserDAO;
import lk.ijse.cropsMonitoring.dto.impl.UserDTO;
import lk.ijse.cropsMonitoring.jwtmodels.JwtAuthResponse;
import lk.ijse.cropsMonitoring.jwtmodels.SignIn;
import lk.ijse.cropsMonitoring.service.AuthenticationService;
import lk.ijse.cropsMonitoring.service.JWTService;
import lk.ijse.cropsMonitoring.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceIMPL implements AuthenticationService {

    private final UserDAO userDAO;
    private final JWTService jwtService;
    private final Mapping mapping;

    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(),signIn.getPassword()));
        var userByEmail = userDAO.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(userByEmail);
        return JwtAuthResponse.builder().token(generatedToken).build() ;

    }

    @Override
    public JwtAuthResponse signUp(UserDTO signUp) {
        var savedUser = userDAO.save(mapping.convertUserDTOToUser(signUp));
        var genToken = jwtService.generateToken(savedUser);
        return JwtAuthResponse.builder().token(genToken).build();

    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        var userName = jwtService.extractUsername(accessToken);
        var userEntity =
                userDAO.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(userEntity);
        return JwtAuthResponse.builder().token(refreshToken).build();
    }
    }

