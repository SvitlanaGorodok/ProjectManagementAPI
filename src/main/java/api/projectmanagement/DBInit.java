package api.projectmanagement;

import api.projectmanagement.model.converter.ProjectConverter;
import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dao.EmployeeLevelDao;
import api.projectmanagement.model.dao.PositionDao;
import api.projectmanagement.model.dao.ProjectDao;
import api.projectmanagement.model.dto.ProjectDto;
import api.projectmanagement.repository.EmployeeLevelRepository;
import api.projectmanagement.repository.EmployeeRepository;
import api.projectmanagement.repository.PositionRepository;
import api.projectmanagement.repository.ProjectRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DBInit {
    private final EmployeeLevelRepository employeeLevelRepository;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final ProjectRepository projectRepository;
    private final ProjectConverter projectConverter;

    @PostConstruct
    public void dbinit() {
//        ProjectDao projectDao = new ProjectDao();
//        projectDao.setId(UUID.randomUUID());
//        projectDao.setName("Name");
//        projectDao.setStartDate(Date.valueOf("2023-01-01"));
//        projectDao.setEndDate(Date.valueOf("2023-01-31"));
//        PositionDao positionDao = new PositionDao(UUID.randomUUID(), "Test", new ArrayList<>());
//        EmployeeLevelDao employeeLevelDao = new EmployeeLevelDao(UUID.randomUUID(), "Test", new ArrayList<>());
//        EmployeeDao employeeDao = new EmployeeDao(UUID.randomUUID(), "Test", "Test", "Test",
//                positionDao, employeeLevelDao, new ArrayList<>());
//        projectDao.setEmployees(List.of(employeeDao));
//        ProjectDto projectDto = projectConverter.toDto(projectDao);
//        System.out.println(projectDao.getName());
//        System.out.println(projectDto.getName());

    }
}
