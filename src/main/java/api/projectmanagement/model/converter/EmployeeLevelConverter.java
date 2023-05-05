package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.EmployeeLevelDao;
import api.projectmanagement.model.dto.EmployeeLevelDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EmployeeLevelConverter {
    public EmployeeLevelDao toDao(EmployeeLevelDto employeeLevelDto){
        return new EmployeeLevelDao(employeeLevelDto.getId(), employeeLevelDto.getName(), new ArrayList<>());
    }

    public EmployeeLevelDto toDto(EmployeeLevelDao employeeLevelDao){
        return new EmployeeLevelDto(employeeLevelDao.getId(), employeeLevelDao.getName());
    }
}
