package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.EmployeeLevelDao;
import api.projectmanagement.model.dto.EmployeeLevelDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class EmployeeLevelConverter {
    public EmployeeLevelDao toDao(EmployeeLevelDto employeeLevelDto){
        EmployeeLevelDao employeeLevelDao = new EmployeeLevelDao();
        employeeLevelDao.setId(employeeLevelDto.getId());
        employeeLevelDao.setName(employeeLevelDto.getName());
        employeeLevelDao.setEmployees(new ArrayList<>());
        return employeeLevelDao;
    }

    public EmployeeLevelDao toDaoById(String employeeLevelDtoId){
        EmployeeLevelDao employeeLevelDao = new EmployeeLevelDao();
        employeeLevelDao.setId(UUID.fromString(employeeLevelDtoId));
        employeeLevelDao.setName("");
        employeeLevelDao.setEmployees(new ArrayList<>());
        return employeeLevelDao;
    }

    public EmployeeLevelDto toDto(EmployeeLevelDao employeeLevelDao){
        EmployeeLevelDto employeeLevelDto = new EmployeeLevelDto();
        employeeLevelDto.setId(employeeLevelDao.getId());
        employeeLevelDto.setName(employeeLevelDao.getName());
        return employeeLevelDto;
    }
}
