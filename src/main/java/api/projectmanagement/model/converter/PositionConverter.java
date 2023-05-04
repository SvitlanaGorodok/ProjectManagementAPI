package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.PositionDao;
import api.projectmanagement.model.dto.PositionDto;

import java.util.ArrayList;

public class PositionConverter {
    public PositionDao toDao(PositionDto positionDto){
        return new PositionDao(positionDto.getId(), positionDto.getName(), new ArrayList<>());
    }

    public PositionDto toDto(PositionDao positionDao){
        return new PositionDto(positionDao.getId(), positionDao.getName());
    }
}
