package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.CropDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringLogServiceDAO extends JpaRepository<CropDetailsEntity,String> {
}
