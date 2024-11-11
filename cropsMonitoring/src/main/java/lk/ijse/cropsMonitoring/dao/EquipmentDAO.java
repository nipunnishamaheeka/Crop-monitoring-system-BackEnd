package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDAO extends JpaRepository<EquipmentEntity,String> {
    @Query("SELECT e.equipmentId FROM EquipmentEntity e ORDER BY e.equipmentId DESC LIMIT 1")
    String findLastEquipmentCode();
}
