package lk.ijse.cropsMonitoring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "field_code")
    private String code;
    @Column(name = "field_name")
    private String fieldName;
    @Column(name = "field_location")
    private Point fieldLocation;
    @Column(name = "field_size")
    private double fieldSize;
    @Column(name = "image_1", columnDefinition = "LONGTEXT")
    private String image1;
    @Column(name = "image_2", columnDefinition = "LONGTEXT")
    private String image2;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CropEntity> crop;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EquipmentEntity> equipment;

    @ManyToMany
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "field_code"),
            inverseJoinColumns = @JoinColumn(name = "staff_member_id")
    )
    private List<StaffEntity> staff;

    @ManyToMany(mappedBy = "field")
    @JsonIgnore
    private List<CropDetailsEntity> cropDetails;

}
