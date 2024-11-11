package lk.ijse.cropsMonitoring.dto.impl;

import lk.ijse.cropsMonitoring.customObj.MonitoringLogServiceResponse;
import lk.ijse.cropsMonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoringLogServiceDTO implements SuperDTO, MonitoringLogServiceResponse {

    private String logCode;
    private Date logDate;
    private String monitoringDetails;

    private String generalImage;

    private List<String> fieldCodes;  // List of Field codes relevant to the log
    private List<String> cropCodes;   // List of Crop codes relevant to the log
    private List<String> staffIds;


}
