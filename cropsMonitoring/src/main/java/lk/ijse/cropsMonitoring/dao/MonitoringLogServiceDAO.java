package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.MonitoringLogServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringLogServiceDAO extends JpaRepository<MonitoringLogServiceEntity,String> {
}
