package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.dao.CropDAO;
import lk.ijse.cropsMonitoring.service.CropService;
import lk.ijse.cropsMonitoring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mapping mapping;
}
