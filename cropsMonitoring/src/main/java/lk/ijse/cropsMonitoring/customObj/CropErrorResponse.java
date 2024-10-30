package lk.ijse.cropsMonitoring.customObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CropErrorResponse implements Serializable,CropResponse {
    private int errorCode;
    private String errorMessage;
}
