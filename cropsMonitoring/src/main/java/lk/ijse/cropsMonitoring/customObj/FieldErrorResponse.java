package lk.ijse.cropsMonitoring.customObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldErrorResponse implements Serializable,FieldResponse {
    private int errorCode;
    private String errorMessage;
}
