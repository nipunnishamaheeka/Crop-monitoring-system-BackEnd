package lk.ijse.cropsMonitoring.customObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonitoringLogServiceErrorResponse implements Serializable,MonitoringLogServiceResponse {
    private int errorCode;
    private String errorMessage;
}
