package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "field")
public class FieldEntity  implements SuperEntity{
    @Id
    @Column(name = "field_code", nullable = false)
    private String code;
    private String name;
    private Point location;
    private Double size_of_Field;

    @Column(name = "f_image1", columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(name = "f_image2", columnDefinition = "LONGTEXT")
    private String fieldImage2;

    @OneToMany(mappedBy = "field")
    private List<CropEntity> crops;  // Changed to List<CropEntity>

//    @OneToMany(mappedBy = "field")
//    @JoinColumn(name = "crop_code")
//    private CropEntity crops;
//
//    @ManyToMany
//    @JoinTable(
//            name = "field_staff",
//            joinColumns = @JoinColumn(name = "field_code"),
//            inverseJoinColumns = @JoinColumn(name = "staff_id")
//    )
//    private List<StaffEntity> staffList;

}
