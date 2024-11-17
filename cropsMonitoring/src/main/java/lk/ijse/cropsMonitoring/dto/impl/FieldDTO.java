package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.customObj.CropResponse;
import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.geo.Point;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO   implements SuperDTO, FieldResponse {
    private String fieldCode;
    private String fieldName;
//    @NotBlank
    private Point fieldLocation;

//    @Positive
    @NotNull // Changed to @NotNull to avoid conflicts
    private double fieldSize;

    @NotNull
    private String image1;

    @NotNull
    private String image2;

    @NotNull
    private List<String> staffId;
}
