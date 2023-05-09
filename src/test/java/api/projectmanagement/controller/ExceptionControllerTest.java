package api.projectmanagement.controller;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.model.converter.ProjectConverter;
import api.projectmanagement.model.dto.ProjectDto;
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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExceptionControllerTest {

    @Mock
    private EmployeeService employeeService;
    @Mock
    private ProjectService projectService;

    @InjectMocks
    private EmployeeController employeeController;

    @InjectMocks
    private ProjectController projectController;

    @InjectMocks
    private ExceptionControllerAdvisor controllerAdvisor;

    private MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void testHandleNoSuchElementFoundExceptionEmployeeController() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(controllerAdvisor)
                .build();

        UUID id = UUID.randomUUID();
        String msg = "Employee with id " + id + " not found!";

        when(employeeService.findById(id)).thenThrow(new NoSuchEntityFoundException(msg));

        mockMvc.perform(get("/employees/update/{id}", id.toString()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchEntityFoundException))
                .andExpect(view().name("exception"))
                .andExpect(model().attribute("msg", msg));
    }

    @Test
    public void testHandleNoSuchElementFoundExceptionProjectControllerCreate() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                .setControllerAdvice(controllerAdvisor)
                .build();

        ProjectDto projectDto = new ProjectDto();
        String msg = "Couldn't create project! Employees not found!";
        List<String> values = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("employeeIds", values);

        when(projectService.save(projectDto)).thenThrow(new NoSuchEntityFoundException(msg));

        mockMvc.perform(post("/projects/create")
                        .param("name", "name")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-01")
                        .params(params)
                        .flashAttr("projectDto", projectDto))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchEntityFoundException))
                .andExpect(view().name("exception"))
                .andExpect(model().attribute("msg", msg));
    }

    @Test
    public void testHandleNoSuchElementFoundExceptionEmployeeControllerUpdate() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                .setControllerAdvice(controllerAdvisor)
                .build();

        UUID id = UUID.randomUUID();
        String msg = "Project with id " + id + " not found!";

        when(projectService.findById(id)).thenThrow(new NoSuchEntityFoundException(msg));

        mockMvc.perform(get("/projects/update/{id}", id.toString()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchEntityFoundException))
                .andExpect(view().name("exception"))
                .andExpect(model().attribute("msg", msg));
    }
}
