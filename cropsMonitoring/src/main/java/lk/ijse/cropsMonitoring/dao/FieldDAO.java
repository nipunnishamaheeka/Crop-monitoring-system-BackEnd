package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.CropEntity;
import lk.ijse.cropsMonitoring.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDAO extends JpaRepository<FieldEntity,String> {
    @Query("SELECT c.code FROM FieldEntity c ORDER BY c.code DESC LIMIT 1")
    String findLastFieldCode();
}
