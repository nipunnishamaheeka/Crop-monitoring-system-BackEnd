package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.CropDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CropDetailsDAO extends JpaRepository<CropDetailsEntity,String> {
    @Query("SELECT l.logCode FROM CropDetailsEntity l ORDER BY l.logCode DESC LIMIT 1")
    String findLastLogCode(long logCount);

}
