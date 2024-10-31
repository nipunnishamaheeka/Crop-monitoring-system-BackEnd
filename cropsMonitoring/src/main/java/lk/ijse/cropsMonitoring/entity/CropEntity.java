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
@Table(name = "crops")
public class CropEntity implements SuperEntity {

    @Id
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private Long cropImage;
    private String category;
    private String cropSeason;

    @ManyToOne
    private FieldEntity field;

}
