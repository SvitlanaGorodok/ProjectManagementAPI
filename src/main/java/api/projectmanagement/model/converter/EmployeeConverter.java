package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmployeeConverter {
    private final PositionConverter positionConverter;
    private final EmployeeLevelConverter employeeLevelConverter;

    public EmployeeDao toDao(EmployeeDto employeeDto){
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.setId(employeeDto.getId());
        employeeDao.setFirstName(employeeDto.getFirstName());
        employeeDao.setLastName(employeeDto.getLastName());
        employeeDao.setEmail(employeeDto.getEmail());
        employeeDao.setPosition(positionConverter.toDaoById(employeeDto.getPositionId()));
        employeeDao.setLevel(employeeLevelConverter.toDaoById(employeeDto.getLevelId()));
        employeeDao.setProjects(new ArrayList<>());
        return employeeDao;
    }

    public EmployeeDao toDaoById(UUID employeeDtoId){
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.setId(employeeDtoId);
        employeeDao.setFirstName("");
        employeeDao.setLastName("");
        employeeDao.setEmail("");
        employeeDao.setPosition(positionConverter.toDaoById(employeeDtoId));
        employeeDao.setLevel(employeeLevelConverter.toDaoById(employeeDtoId));
        employeeDao.setProjects(new ArrayList<>());
        return employeeDao;
    }

    public EmployeeDto toDto(EmployeeDao employeeDao){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeDao.getId());
        employeeDto.setFirstName(employeeDao.getFirstName());
        employeeDto.setLastName(employeeDao.getLastName());
        employeeDto.setEmail(employeeDao.getEmail());
        employeeDto.setPositionId(employeeDao.getPosition().getId());
        employeeDto.setLevelId(employeeDao.getLevel().getId());
        employeeDto.setPositionDetails(employeeDao.getLevel().getName() + " " + employeeDao.getPosition().getName());
        return employeeDto;
    }
}
