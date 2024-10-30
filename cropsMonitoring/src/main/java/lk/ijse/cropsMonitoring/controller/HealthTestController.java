package lk.ijse.cropsMonitoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/crops")
public class HealthTestController {
    @GetMapping("health")
    public String healthCheck() {
        return "Crops Controller is OK";
    }


}
