package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.UserErrorResponse;
import lk.ijse.cropsMonitoring.customObj.UserResponse;
import lk.ijse.cropsMonitoring.dao.UserDAO;
import lk.ijse.cropsMonitoring.dto.impl.UserDTO;
import lk.ijse.cropsMonitoring.entity.UserEntity;
import lk.ijse.cropsMonitoring.exception.DataPersistFailedException;
import lk.ijse.cropsMonitoring.exception.NotFoundException;
import lk.ijse.cropsMonitoring.service.UserService;
import lk.ijse.cropsMonitoring.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final Mapping mapping;

    @Override
    public void saveUser(UserDTO user) {
        Optional<UserEntity> existsUser = userDAO.findByEmail(user.getEmail());
        if (!existsUser.isPresent()) {
            UserEntity save = userDAO.save(mapping.convertUserDTOToUser(user));
            System.out.println(userDAO);
            System.out.println("User saved successfully with email: {}"+ user.getEmail());
            if (save == null) {
                throw new DataPersistFailedException("User save failed");
            }
        }else {
            throw new DataPersistFailedException("User already exists");
        }
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        Optional<UserEntity> user = userDAO.findByEmail(email);
        if (user.isPresent()) {
            return (UserResponse) mapping.convertUserToUserDTO(user.get());
        }else {
            return new UserErrorResponse("0","User not found");
        }
    }

    @Override
    public void updateUser(UserDTO user, String email) {
        Optional<UserEntity> existsUser = userDAO.findByEmail(email);
        if (existsUser.isPresent()) {
            existsUser.get().setPassword(user.getPassword());
        }else {
            throw new NotFoundException("User not exists");
        }
    }

//    @Override
//    public UserDetailsService userDetailsService() {
//        return email ->
//                userDAO.findByEmail(email)
//                        .orElseThrow(()-> new NotFoundException("User Not found"));
//    }
}
