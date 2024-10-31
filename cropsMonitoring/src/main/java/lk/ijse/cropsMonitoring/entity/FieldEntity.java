package lk.ijse.cropsMonitoring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Field")
public class FieldEntity  implements SuperEntity{
    @Id
    private String code;
    private String name;
    private Point location;
    private Double size_of_Field;
    //    @ElementCollection
//    private List<String> crops;
//    @ElementCollection
//    private List<String> staff;
    private String crops;
    private String staff;
    private Long image1;
    private Long image2;
}
