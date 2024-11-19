package lk.ijse.cropsMonitoring.util;

import lk.ijse.cropsMonitoring.dto.impl.*;
import lk.ijse.cropsMonitoring.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;
    //CropDetails
    public CropDetailsEntity convertCropDetailsDTOToCropDetails(CropDetailsDTO cropDetailsDTO){
        return modelMapper.map(cropDetailsDTO, CropDetailsEntity.class);
    }

    public CropDetailsDTO convertCropDetailsToCropDetailsDTO(CropDetailsEntity cropDetails){
        return modelMapper.map(cropDetails, CropDetailsDTO.class);
    }

    public List convertCropDetailsListToCropDetailsDTOList(List<CropDetailsEntity> cropDetails){
        return modelMapper.map(cropDetails, List.class);
    }
//Crop
    public CropDTO toCropsDto(CropEntity entity) {
        return modelMapper.map(entity, CropDTO.class);
    }

    public CropEntity toCropsEntity(CropDTO dto) {
        return modelMapper.map(dto, CropEntity.class);
    }

    public List<CropDTO> toCropsDtoList(List<CropEntity> entityList) {
        return modelMapper.map(entityList, List.class);
    }

    //vehicle
    public VehicleManagementDTO toVehicleDto(VehicleManagementEntity entity) {
        return modelMapper.map(entity, VehicleManagementDTO.class);
    }

    public VehicleManagementEntity toVehicleEntity(VehicleManagementDTO dto) {
        return modelMapper.map(dto, VehicleManagementEntity.class);
    }

    public List<VehicleManagementDTO> toVehicleDtoList(List<VehicleManagementEntity> entityList) {
        return modelMapper.map(entityList, List.class);
    }

    //field
    public FieldEntity toFieldEntity(FieldDTO dto) {
        return modelMapper.map(dto, FieldEntity.class);
    }
    public FieldDTO toFieldDto(FieldEntity entity) {
        return modelMapper.map(entity, FieldDTO.class);
    }

    public List<FieldDTO> toFieldDtoList(List<FieldEntity> entityList) {
        return modelMapper.map(entityList, List.class);
    }

    //Equipment
    public EquipmentEntity toEquipmentEntity(EquipmentDTO dto) {
        return modelMapper.map(dto, EquipmentEntity.class);
    }
    public EquipmentDTO toEquipmentDto(EquipmentEntity entity) {
        return modelMapper.map(entity, EquipmentDTO.class);
    }

    public List<EquipmentDTO> toEquipmentDtoList(List<EquipmentEntity> entityList) {
        return modelMapper.map(entityList, List.class);
    }

    //Staff
    public StaffEntity toStaffEntity(StaffDTO dto) {
        return modelMapper.map(dto, StaffEntity.class);
    }
    public StaffDTO toStaffDto(StaffEntity entity) {
        return modelMapper.map(entity, StaffDTO.class);
    }

    public List<StaffDTO> toStaffDtoList(List<StaffEntity> entityList) {
        return modelMapper.map(entityList, List.class);
    }
}
