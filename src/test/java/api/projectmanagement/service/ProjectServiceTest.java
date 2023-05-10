package api.projectmanagement.service;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.model.converter.EmployeeConverter;
import api.projectmanagement.model.converter.EmployeeLevelConverter;
import api.projectmanagement.model.converter.PositionConverter;
import api.projectmanagement.model.converter.ProjectConverter;
import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dao.ProjectDao;
import api.projectmanagement.model.dto.FindProjectParam;
import api.projectmanagement.model.dto.ProjectDto;
import api.projectmanagement.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {
    @Mock
    private ProjectRepository repository;
    private AutoCloseable closeable;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        projectService = new ProjectService(new ProjectConverter( new EmployeeConverter(
                new PositionConverter(), new EmployeeLevelConverter())), repository);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void testSaveAndConverter() {
        UUID id = UUID.randomUUID();
        UUID employeeId1 = UUID.randomUUID();
        UUID employeeId2 = UUID.randomUUID();
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(id);
        projectDto.setName("name");
        projectDto.setStartDate(Date.valueOf("2022-01-01"));
        projectDto.setEndDate(Date.valueOf("2022-02-01"));
        projectDto.setEmployeeIds(List.of(employeeId1, employeeId2));
        when(repository.save(any(ProjectDao.class))).thenAnswer(i -> i.getArguments()[0]);
        ProjectDto saved = projectService.save(projectDto);
        assertThat(projectDto.getId()).isSameAs(saved.getId());
        assertThat(projectDto.getName()).isSameAs(saved.getName());
        assertThat(projectDto.getStartDate()).isSameAs(saved.getStartDate());
        assertThat(projectDto.getEndDate()).isSameAs(saved.getEndDate());
        assertThat(projectDto.getEmployeeIds().get(0)).isSameAs(saved.getEmployeeIds().get(0));
        assertThat(projectDto.getEmployeeIds().get(1)).isSameAs(saved.getEmployeeIds().get(1));
    }

    @Test
    public void testSaveWithNullId() {
        when(repository.save(any(ProjectDao.class))).thenAnswer(i -> i.getArguments()[0]);
        ProjectDto toSave = new ProjectDto();
        toSave.setEmployeeIds(List.of(UUID.randomUUID(), UUID.randomUUID()));
        assertNull(toSave.getId());
        ProjectDto saved = projectService.save(toSave);
        assertNotNull(saved.getId());
    }

    @Test
    public void testFindAll() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID employeeId1 = UUID.randomUUID();
        UUID employeeId2 = UUID.randomUUID();
        EmployeeDao employeeDao1 = new EmployeeDao();
        employeeDao1.setId(employeeId1);
        EmployeeDao employeeDao2 = new EmployeeDao();
        employeeDao2.setId(employeeId2);

        ProjectDto projectDto1 = new ProjectDto();
        projectDto1.setId(id1);
        projectDto1.setEmployeeIds(List.of(employeeId1, employeeId2));
        ProjectDto projectDto2 = new ProjectDto();
        projectDto2.setId(id2);
        projectDto2.setEmployeeIds(List.of(employeeId1, employeeId2));
        List<ProjectDto> projectDtos = List.of(projectDto1, projectDto2);

        ProjectDao projectDao1 = new ProjectDao();
        projectDao1.setId(id1);
        projectDao1.setEmployees(List.of(employeeDao1, employeeDao2));
        ProjectDao projectDao2 = new ProjectDao();
        projectDao2.setId(id2);
        projectDao2.setEmployees(List.of(employeeDao1, employeeDao2));
        List<ProjectDao> projectDaos = List.of(projectDao1, projectDao2);

        when(repository.findAll()).thenReturn(projectDaos);
        List<ProjectDto> findAll = projectService.findAll();

        assertThat(findAll.get(0).getId()).isSameAs(projectDtos.get(0).getId());
        assertThat(findAll.get(1).getId()).isSameAs(projectDtos.get(1).getId());
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.setId(employeeId);
        ProjectDao projectDao = new ProjectDao();
        projectDao.setId(id);
        projectDao.setEmployees(List.of(employeeDao));

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(projectDao));

        ProjectDto findById = projectService.findById(id);
        assertThat(findById.getId()).isSameAs(id);

    }

    @Test
    public void testFindByThrowNoSuchEntityFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> projectService.findById(id));
        assertEquals("Project with id " + id + " not found!", exception.getMessage());
    }

    @Test
    public void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(any(UUID.class));
        projectService.deleteById(id);
        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    public void testProjectNamesEmails(){
        List<String> projectNames = List.of("name1", "name2");
        when(repository.findAllNames()).thenReturn(projectNames);
        assertThat(projectNames).isSameAs(projectService.findAllNames());
    }

    @Test
    public void testFindByParameters() {
        UUID id = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(id);
        projectDto.setName("name");
        projectDto.setStartDate(Date.valueOf("2022-01-01"));
        projectDto.setEndDate(Date.valueOf("2022-02-01"));
        projectDto.setEmployeeIds(List.of(employeeId));

        ProjectDao projectDao = new ProjectDao();
        projectDao.setId(id);
        projectDao.setName("name");
        projectDao.setStartDate(Date.valueOf("2022-01-01"));
        projectDao.setEndDate(Date.valueOf("2022-02-01"));
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.setId(employeeId);
        projectDao.setEmployees(List.of(employeeDao));

        FindProjectParam projectParam = new FindProjectParam();
        projectParam.setName("name");
        projectParam.setStartDateFrom("2022-01-01");

        when(repository.findByParameters("%name%", Date.valueOf("2022-01-01"), null,
                null, null)).thenReturn(List.of(projectDao));

        ProjectDto findProject = projectService.findByParameters(projectParam).get(0);

        assertThat(findProject.getId()).isSameAs(projectDto.getId());
        assertThat(findProject.getName()).isSameAs(projectDto.getName());
        assertEquals(findProject.getStartDate(), projectDto.getStartDate());
        assertEquals(findProject.getEndDate(), projectDto.getEndDate());
    }
}
