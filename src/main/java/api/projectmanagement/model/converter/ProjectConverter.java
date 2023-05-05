package api.projectmanagement.model.converter;

import api.projectmanagement.model.dao.ProjectDao;
import api.projectmanagement.model.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectConverter {
    private final EmployeeConverter employeeConverter;

    public ProjectDao toDao(ProjectDto projectDto){
//        return new ProjectDao(projectDto.getId(), projectDto.getName(), projectDto.getStartDate(), projectDto.getEndDate(),
//                projectDto.getEmployees().stream()
//                        .map(employeeConverter::toDao)
//                        .collect(Collectors.toList()));
        return new ProjectDao();
    }

    public ProjectDto toDto(ProjectDao projectDao){
//        return new ProjectDto(projectDao.getId(), projectDao.getName(), projectDao.getStartDate(), projectDao.getEndDate(),
//                projectDao.getEmployees().stream()
//                        .map(employeeConverter::toDto)
//                        .collect(Collectors.toList()));
        return new ProjectDto();
    }
}
