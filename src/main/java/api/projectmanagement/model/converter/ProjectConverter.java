package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dao.ProjectDao;
import api.projectmanagement.model.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectConverter {
    private final EmployeeConverter employeeConverter;

    public ProjectDao toDao(ProjectDto projectDto) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setId(projectDto.getId());
        projectDao.setName(projectDto.getName());
        projectDao.setStartDate(projectDto.getStartDate());
        projectDao.setEndDate(projectDto.getEndDate());
        projectDao.setEmployees(projectDto.getEmployeeIds().stream()
                .map(employeeConverter::toDaoById)
                .collect(Collectors.toList())
        );
        return projectDao;
    }

    public ProjectDto toDto(ProjectDao projectDao) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(projectDao.getId());
        projectDto.setName(projectDao.getName());
        projectDto.setStartDate(projectDao.getStartDate());
        projectDto.setEndDate(projectDao.getEndDate());
        projectDto.setEmployeeIds(projectDao.getEmployees().stream()
                .map(EmployeeDao::getId)
                .collect(Collectors.toList()));
        projectDto.setProjectsDetails(projectDao.getEmployees().stream()
                .map(this::getEmployeeDetails)
                .collect(Collectors.toList()));
        return projectDto;
    }

    private String getEmployeeDetails(EmployeeDao employee){
        return employee.getFirstName().charAt(0) + ". " + employee.getLastName() +
                " - " + employee.getLevel().getName() + " " + employee.getPosition().getName() +
                " email: " + employee.getEmail();
    }
}
