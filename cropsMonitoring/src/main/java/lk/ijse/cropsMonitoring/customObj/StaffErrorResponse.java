package lk.ijse.cropsMonitoring.customObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffErrorResponse implements Serializable,StaffResponse {
    private int errorCode;
    private String errorMessage;
}
