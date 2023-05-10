package api.projectmanagement.controller;

import api.projectmanagement.model.dto.*;
import api.projectmanagement.service.EmployeeService;
import api.projectmanagement.service.ProjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

public class ProjectControllerTest {
    @Mock
    private ProjectService projectService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private ProjectController projectController;

    private MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void testShowAll() throws Exception {
        List<ProjectDto> projects = new ArrayList<>();
        projects.add(new ProjectDto());
        projects.add(new ProjectDto());

        when(projectService.findAll()).thenReturn(projects);
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/showall"))
                .andExpect(model().attribute("projects", hasSize(2)));
    }

    @Test
    public void testCreateForm() throws Exception {
        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(new EmployeeDto());
        employees.add(new EmployeeDto());

        List<String> projectNames = new ArrayList<>();
        projectNames.add("");
        projectNames.add("");

        when(employeeService.findAll()).thenReturn(employees);
        when(projectService.findAllNames()).thenReturn(projectNames);

        mockMvc.perform(get("/projects/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/create"))
                .andExpect(model().attribute("employees", hasSize(2)))
                .andExpect(model().attribute("projectNames", hasSize(2)));

    }

    @Test
    public void testCreate() throws Exception {
        List<String> values = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("employeeIds", values);

        mockMvc.perform(post("/projects/create")
                        .param("name", "name")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-01")
                        .params(params)
                        .flashAttr("projectDto", new ProjectDto()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/projects/delete/{id}", UUID.randomUUID().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    public void testUpdateForm() throws Exception {
        UUID id = UUID.randomUUID();

        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(new EmployeeDto());
        employees.add(new EmployeeDto());

        List<String> projectNames = new ArrayList<>();
        projectNames.add("");
        projectNames.add("");

        ProjectDto project = new ProjectDto();
        project.setId(id);
        project.setName("name");

        when(employeeService.findAll()).thenReturn(employees);
        when(projectService.findAllNames()).thenReturn(projectNames);
        when(projectService.findById(id)).thenReturn(project);

        mockMvc.perform(get("/projects/update/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/update"))
                .andExpect(model().attribute("project", project))
                .andExpect(model().attribute("employees", hasSize(2)))
                .andExpect(model().attribute("projectNames", hasSize(2)));
    }

    @Test
    public void testUpdate() throws Exception {
        List<String> values = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("employeeIds", values);
        mockMvc.perform(post("/projects/update")
                        .param("name", "name")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-01")
                        .params(params)
                        .flashAttr("projectDto", new ProjectDto()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    public void testDetails() throws Exception{
        UUID id = UUID.randomUUID();
        ProjectDto project = new ProjectDto();
        project.setId(id);
        project.setName("name");
        project.setEmployeeIds(new ArrayList<>());

        List<EmployeeDto> employees = new ArrayList<>();

        when(projectService.findById(id)).thenReturn(project);
        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(get("/projects/details/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/details"))
                .andExpect(model().attribute("project", project))
                .andExpect(model().attribute("employees", employees));
    }

    @Test
    public void testFindForm() throws Exception {

        mockMvc.perform(get("/projects/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/find"));
    }

    @Test
    public void testFind() throws Exception {
        List<ProjectDto> projects = new ArrayList<>();
        projects.add(new ProjectDto());
        projects.add(new ProjectDto());

        when(projectService.findByParameters(any(FindProjectParam.class))).thenReturn(projects);

        mockMvc.perform(post("/projects/find")
                        .param("name", "name")
                        .param("startDateFrom", "2023-01-01")
                        .param("startDateTo", "2023-01-01")
                        .param("endDateFrom", "2023-01-01")
                        .param("endDateTo", "2023-01-01")
                        .flashAttr("projectDto", new ProjectDto()))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/find"))
                .andExpect(model().attribute("projects", hasSize(2)));
    }
}
