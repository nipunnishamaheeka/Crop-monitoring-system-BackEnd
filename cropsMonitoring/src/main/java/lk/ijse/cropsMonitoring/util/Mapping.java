package lk.ijse.cropsMonitoring.util;

import lk.ijse.cropsMonitoring.dto.impl.CropDTO;
import lk.ijse.cropsMonitoring.entity.CropEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;

    public CropDTO toCropsDto(CropEntity entity) {
        return modelMapper.map(entity, CropDTO.class);
    }

    public CropEntity toCropsEntity(CropDTO dto) {
        return modelMapper.map(dto, CropEntity.class);
    }

    public List<CropDTO> toCropsDtoList(List<CropEntity> entityList) {
        return modelMapper.map(entityList, List.class);
    }


}
