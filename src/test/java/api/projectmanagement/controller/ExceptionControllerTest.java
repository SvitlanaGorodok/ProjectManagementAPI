package api.projectmanagement.controller;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExceptionControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @InjectMocks
    private ExceptionControllerAdvisor controllerAdvisor;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(controllerAdvisor)
                .build();
    }

    @Test
    public void testHandleNoSuchElementFoundException() throws Exception {
        UUID id = UUID.randomUUID();
        String msg = "Employee with id " + id + " not found!";

        when(employeeService.findById(id)).thenThrow(new NoSuchEntityFoundException(msg));

        mockMvc.perform(get("/employees/update/{id}", id.toString()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchEntityFoundException))
                .andExpect(view().name("exception"))
                .andExpect(model().attribute("msg", msg));
    }
}
