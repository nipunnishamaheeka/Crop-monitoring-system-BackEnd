package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDAO extends JpaRepository<FieldEntity,String> {
}
