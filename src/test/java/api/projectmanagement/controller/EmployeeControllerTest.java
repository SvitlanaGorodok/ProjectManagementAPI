package api.projectmanagement.controller;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.model.dto.EmployeeDto;
import api.projectmanagement.model.dto.EmployeeLevelDto;
import api.projectmanagement.model.dto.PositionDto;
import api.projectmanagement.service.EmployeeLevelService;
import api.projectmanagement.service.EmployeeService;
import api.projectmanagement.service.PositionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;
    @Mock
    private PositionService positionService;
    @Mock
    private EmployeeLevelService levelService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void testShowAll() throws Exception {
        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(new EmployeeDto());
        employees.add(new EmployeeDto());

        when(employeeService.findAll()).thenReturn(employees);
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/showall"))
                .andExpect(model().attribute("employees", hasSize(2)));
    }

    @Test
    public void testCreateForm() throws Exception {
        List<PositionDto> positions = new ArrayList<>();
        positions.add(new PositionDto());
        positions.add(new PositionDto());

        List<EmployeeLevelDto> levels = new ArrayList<>();
        levels.add(new EmployeeLevelDto());
        levels.add(new EmployeeLevelDto());

        List<String> emails = new ArrayList<>();
        emails.add("");
        emails.add("");

        when(positionService.findAll()).thenReturn(positions);
        when(levelService.findAll()).thenReturn(levels);
        when(employeeService.findAllEmails()).thenReturn(emails);

        mockMvc.perform(get("/employees/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/create"))
                .andExpect(model().attribute("positions", hasSize(2)))
                .andExpect(model().attribute("levels", hasSize(2)))
                .andExpect(model().attribute("emails", hasSize(2)));

    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post("/employees/create")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "email")
                        .param("positionId", UUID.randomUUID().toString())
                        .param("levelId", UUID.randomUUID().toString())
                        .flashAttr("employeeDto", new EmployeeDto()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/employees/delete/{id}", UUID.randomUUID().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    public void testUpdateForm() throws Exception {
        UUID id = UUID.randomUUID();

        List<PositionDto> positions = new ArrayList<>();
        positions.add(new PositionDto());
        positions.add(new PositionDto());

        List<EmployeeLevelDto> levels = new ArrayList<>();
        levels.add(new EmployeeLevelDto());
        levels.add(new EmployeeLevelDto());

        List<String> emails = new ArrayList<>();
        emails.add("");
        emails.add("");

        EmployeeDto employee = new EmployeeDto();
        employee.setId(id);
        employee.setEmail("email");

        when(positionService.findAll()).thenReturn(positions);
        when(levelService.findAll()).thenReturn(levels);
        when(employeeService.findAllEmails()).thenReturn(emails);
        when(employeeService.findById(id)).thenReturn(employee);

        mockMvc.perform(get("/employees/update/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/update"))
                .andExpect(model().attribute("employee", employee))
                .andExpect(model().attribute("positions", hasSize(2)))
                .andExpect(model().attribute("levels", hasSize(2)))
                .andExpect(model().attribute("emails", hasSize(2)));
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(post("/employees/update")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "email")
                        .param("positionId", UUID.randomUUID().toString())
                        .param("levelId", UUID.randomUUID().toString())
                        .flashAttr("employeeDto", new EmployeeDto()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    public void testFindForm() throws Exception {
        List<PositionDto> positions = new ArrayList<>();
        positions.add(new PositionDto());
        positions.add(new PositionDto());

        List<EmployeeLevelDto> levels = new ArrayList<>();
        levels.add(new EmployeeLevelDto());
        levels.add(new EmployeeLevelDto());

        when(positionService.findAll()).thenReturn(positions);
        when(levelService.findAll()).thenReturn(levels);

        mockMvc.perform(get("/employees/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/find"))
                .andExpect(model().attribute("positions", hasSize(2)))
                .andExpect(model().attribute("levels", hasSize(2)));
    }

    @Test
    public void testFind() throws Exception {
        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(new EmployeeDto());
        employees.add(new EmployeeDto());

        List<PositionDto> positions = new ArrayList<>();
        positions.add(new PositionDto());
        positions.add(new PositionDto());

        List<EmployeeLevelDto> levels = new ArrayList<>();
        levels.add(new EmployeeLevelDto());
        levels.add(new EmployeeLevelDto());

        when(positionService.findAll()).thenReturn(positions);
        when(levelService.findAll()).thenReturn(levels);
        when(employeeService.findByParameters(any(EmployeeDto.class))).thenReturn(employees);

        mockMvc.perform(post("/employees/find")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "email")
                        .param("positionId", UUID.randomUUID().toString())
                        .param("levelId", UUID.randomUUID().toString())
                        .flashAttr("employeeDto", new EmployeeDto()))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/find"))
                .andExpect(model().attribute("positions", hasSize(2)))
                .andExpect(model().attribute("levels", hasSize(2)))
                .andExpect(model().attribute("employees", hasSize(2)));
    }
}
