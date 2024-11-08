package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.VehicleManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleManagementDAO extends JpaRepository<VehicleManagementEntity, String> {
    @Query("SELECT v.vehicleCode FROM VehicleManagementEntity v ORDER BY v.vehicleCode DESC LIMIT 1")
    String findLastVehicleCode();
}
