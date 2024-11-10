package lk.ijse.cropsMonitoring.service.impl;

import lk.ijse.cropsMonitoring.customObj.FieldResponse;
import lk.ijse.cropsMonitoring.dto.impl.FieldDTO;
import lk.ijse.cropsMonitoring.service.FieldService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {
    @Override
    public void save(FieldDTO fieldDTO) {

    }

    @Override
    public void update(String id, FieldDTO fieldDTO) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public FieldResponse getSelectedField(String id) {
        return null;
    }

    @Override
    public List<FieldDTO> getAll() {
        return List.of();
    }
}
