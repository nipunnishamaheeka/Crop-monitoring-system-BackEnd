package lk.ijse.cropsMonitoring.dao;

import lk.ijse.cropsMonitoring.entity.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropDAO extends JpaRepository<CropEntity,String> {
    @Query("SELECT c.cropCode FROM CropEntity c ORDER BY c.cropCode DESC LIMIT 1")
    String findLastCropCode();



    @Query("SELECT c.cropCode FROM CropEntity c WHERE c.cropCode = :cropCode ORDER BY c.cropCode DESC LIMIT 1")
    Optional<CropEntity> findLastCropDetailCode(@Param("cropCode") String cropCode);



//    @Query("SELECT c.cropCode FROM CropEntity c WHERE c.cropCode = :cropCode ORDER BY c.cropCode DESC LIMIT 1")
//    String findLastCropDetailCode(@Param("cropCode") String cropCode);


}
