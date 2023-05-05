package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EmployeeConverter {
    private final PositionConverter positionConverter;
    private final EmployeeLevelConverter employeeLevelConverter;

    public EmployeeDao toDao(EmployeeDto employeeDto){
        return new EmployeeDao(employeeDto.getId(), employeeDto.getFirstName(),
                employeeDto.getLastName(), employeeDto.getEmail(),
                positionConverter.toDao(employeeDto.getPosition()),
                employeeLevelConverter.toDao(employeeDto.getLevel()),
                new ArrayList<>());
    }

    public EmployeeDto toDto(EmployeeDao employeeDao){
        return new EmployeeDto(employeeDao.getId(), employeeDao.getFirstName(),
                employeeDao.getLastName(), employeeDao.getEmail(),
                positionConverter.toDto(employeeDao.getPosition()),
                employeeLevelConverter.toDto(employeeDao.getLevel()));
    }
}
