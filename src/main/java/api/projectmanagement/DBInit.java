package api.projectmanagement;

import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dao.ProjectDao;
import api.projectmanagement.repository.EmployeeLevelRepository;
import api.projectmanagement.repository.EmployeeRepository;
import api.projectmanagement.repository.PositionRepository;
import api.projectmanagement.repository.ProjectRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DBInit {
    private final EmployeeLevelRepository employeeLevelRepository;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final ProjectRepository projectRepository;

    @PostConstruct
    public void dbinit() {
        EmployeeDao employeeDao = employeeRepository.findById(1).get();
        System.out.println(employeeDao);
        ProjectDao project = projectRepository.findAll().get(0);
        System.out.println(project);
    }
}
