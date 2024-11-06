package lk.ijse.cropsMonitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class DataPersistFailedException extends RuntimeException {
    public DataPersistFailedException(String message) {
        super(message);
    }

@ExceptionHandler(DataPersistFailedException.class)
public ResponseEntity<String> handleDataPersistFailedException(DataPersistFailedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}

//    public DataPersistFailedException(String message, Throwable cause) {
//        super(message, cause);
//    }
}
