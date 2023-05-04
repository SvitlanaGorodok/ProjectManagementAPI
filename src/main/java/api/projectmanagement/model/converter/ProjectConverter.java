package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.ProjectDao;
import api.projectmanagement.model.dto.ProjectDto;

import java.util.stream.Collectors;

public class ProjectConverter {
    private final EmployeeConverter employeeConverter = new EmployeeConverter();

    public ProjectDao toDao(ProjectDto projectDto){
        return new ProjectDao(projectDto.getId(), projectDto.getName(), projectDto.getStartDate(), projectDto.getEndDate(),
                projectDto.getEmployees().stream()
                        .map(employeeConverter::toDao)
                        .collect(Collectors.toList()));
    }

    public ProjectDto toDto(ProjectDao projectDao){
        return new ProjectDto(projectDao.getId(), projectDao.getName(), projectDao.getStartDate(), projectDao.getEndDate(),
                projectDao.getEmployees().stream()
                        .map(employeeConverter::toDto)
                        .collect(Collectors.toList()));
    }
}
