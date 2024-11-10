package lk.ijse.cropsMonitoring.controller;

import lk.ijse.cropsMonitoring.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/v1/field", method = RequestMethod.OPTIONS)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class FieldController {

    @Autowired
    private final FieldService fieldService;
    private static final Logger logger = Logger.getLogger(FieldController.class.getName());


}
