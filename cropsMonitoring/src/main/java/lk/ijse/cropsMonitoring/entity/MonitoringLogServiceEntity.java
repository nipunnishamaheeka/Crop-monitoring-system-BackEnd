package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "monitoring_log_Service")
public class MonitoringLogServiceEntity implements SuperEntity {
    @Id
    private String logCode;
    private Date logDate;

    @Column(name = "l_details")
    private String monitoringDetails;

    @Column(name = "g_image", columnDefinition = "LONGTEXT")
    private String generalImage;

//    @ManyToOne
//    @JoinColumn(name = "field_code")
//    private FieldEntity field;
//
//    @ManyToOne
//    @JoinColumn(name = "staff_id")
//    private StaffEntity staff;
//
//    @ManyToOne
//    @JoinColumn(name = "crop_code")
//    private CropEntity crops;


}
