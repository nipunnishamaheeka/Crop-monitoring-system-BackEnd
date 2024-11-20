package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.customObj.CropDetailResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDetailsDTO implements SuperDTO, CropDetailResponse {

    private String logCode;
    private Date logDate;
    private String logDetails;
    private String observedImage;
    private List<String> fieldCodes;
    private List<String> cropCodes;
    private List<String> staffIds;


}
