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

//    @Column(columnDefinition = "Point")
    private String location;
    private Double size_of_Field;

    @OneToMany(mappedBy = "field")
    private List<CropEntity> crops;

    @ManyToMany
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "field_code"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private List<StaffEntity> staff;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] fieldImage1;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] fieldImage2;


//    @Column(name = "f_image1", columnDefinition = "LONGTEXT")
//    private String fieldImage1;
//    @Column(name = "f_image2", columnDefinition = "LONGTEXT")
//    private String fieldImage2;


    @OneToMany(mappedBy = "assignedField",cascade = CascadeType.ALL)
    private List<EquipmentEntity> equipment;

}
