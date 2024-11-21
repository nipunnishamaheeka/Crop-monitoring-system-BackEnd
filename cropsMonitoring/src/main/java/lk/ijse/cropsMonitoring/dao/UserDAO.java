package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDAO extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}
