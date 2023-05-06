package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.PositionDao;
import api.projectmanagement.model.dto.PositionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class PositionConverter {
    public PositionDao toDao(PositionDto positionDto){
        PositionDao positionDao = new PositionDao();
        positionDao.setId(positionDto.getId());
        positionDao.setName(positionDto.getName());
        positionDao.setEmployees(new ArrayList<>());
        return positionDao;
    }

    public PositionDao toDaoById(UUID positionDtoId){
        PositionDao positionDao = new PositionDao();
        positionDao.setId(positionDtoId);
        positionDao.setName("");
        positionDao.setEmployees(new ArrayList<>());
        return positionDao;
    }

    public PositionDto toDto(PositionDao positionDao){
        PositionDto positionDto = new PositionDto();
        positionDto.setId(positionDao.getId());
        positionDto.setName(positionDao.getName());
        return positionDto;
    }
}
