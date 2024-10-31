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
    private String observation;
    private Long generalImage;

    @ElementCollection
    private List<String> tasks;

    @ManyToOne
    private CropEntity crop;

    @ManyToMany
    private List<StaffEntity> staff;
}
