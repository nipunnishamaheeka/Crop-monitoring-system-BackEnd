package lk.ijse.cropsMonitoring.dto.impl;

import jakarta.persistence.ElementCollection;
import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO  implements SuperDTO, CropResponse {
    private String code;
    private String name;
    private Point location;
    private Double size_of_Field;
    private List<String> crops;
    private List<String> staff;
    private Long image1;
    private Long image2;
}
