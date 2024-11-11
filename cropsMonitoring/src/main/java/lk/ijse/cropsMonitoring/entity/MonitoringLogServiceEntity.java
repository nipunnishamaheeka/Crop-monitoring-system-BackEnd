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
    @Temporal(TemporalType.DATE)
    private Date logDate;

    @Column(name = "l_details")
    private String monitoringDetails;

    @Lob
    private String observedImage;

    @ManyToMany
    private List<FieldEntity> fields;

    @ManyToMany
    private List<CropEntity> crops;

    @ManyToMany
    private List<StaffEntity> staff;


}
