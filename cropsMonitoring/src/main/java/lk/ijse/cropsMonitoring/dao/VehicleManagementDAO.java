package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.VehicleManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleManagementDAO extends JpaRepository<VehicleManagementEntity, String> {
}
