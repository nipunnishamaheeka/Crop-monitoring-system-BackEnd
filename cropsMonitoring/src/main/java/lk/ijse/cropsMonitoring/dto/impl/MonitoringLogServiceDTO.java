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

    private String field;
    private String staffId;
    private String crops;


}
