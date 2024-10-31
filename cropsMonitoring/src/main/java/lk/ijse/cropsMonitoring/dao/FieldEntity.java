package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldEntity extends JpaRepository<CropEntity,String> {
}
