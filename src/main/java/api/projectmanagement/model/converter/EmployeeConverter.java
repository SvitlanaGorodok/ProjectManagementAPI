package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dto.EmployeeDto;

import java.util.ArrayList;

public class EmployeeConverter {
    private final PositionConverter positionConverter = new PositionConverter();
    private final EmployeeLevelConverter employeeLevelConverter = new EmployeeLevelConverter();

    public EmployeeDao toDao(EmployeeDto employeeDto){
        return new EmployeeDao(employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(),
                positionConverter.toDao(employeeDto.getPosition()),
                employeeLevelConverter.toDao(employeeDto.getLevel()),
                new ArrayList<>());
    }

    public EmployeeDto toDto(EmployeeDao employeeDao){
        return new EmployeeDto(employeeDao.getId(), employeeDao.getFirstName(), employeeDao.getLastName(),
                positionConverter.toDto(employeeDao.getPosition()),
                employeeLevelConverter.toDto(employeeDao.getLevel()));
    }
}
