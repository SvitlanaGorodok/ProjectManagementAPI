package api.projectmanagement.service;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.model.converter.EmployeeConverter;
import api.projectmanagement.model.converter.EmployeeLevelConverter;
import api.projectmanagement.model.converter.PositionConverter;
import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dao.EmployeeLevelDao;
import api.projectmanagement.model.dao.PositionDao;
import api.projectmanagement.model.dto.EmployeeDto;
import api.projectmanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository repository;
    private AutoCloseable closeable;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(new EmployeeConverter(
                new PositionConverter(), new EmployeeLevelConverter()), repository);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void testSaveAndConverter() {
        UUID id = UUID.randomUUID();
        UUID positionId = UUID.randomUUID();
        UUID levelId = UUID.randomUUID();
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(id);
        employeeDto.setFirstName("first name");
        employeeDto.setLastName("last name");
        employeeDto.setEmail("email");
        employeeDto.setPositionId(positionId);
        employeeDto.setLevelId(levelId);
        when(repository.save(any(EmployeeDao.class))).thenAnswer(i -> i.getArguments()[0]);
        EmployeeDto saved = employeeService.save(employeeDto);
        assertThat(employeeDto.getId()).isSameAs(saved.getId());
        assertThat(employeeDto.getFirstName()).isSameAs(saved.getFirstName());
        assertThat(employeeDto.getLastName()).isSameAs(saved.getLastName());
        assertThat(employeeDto.getEmail()).isSameAs(saved.getEmail());
        assertThat(employeeDto.getPositionId()).isSameAs(saved.getPositionId());
        assertThat(employeeDto.getLevelId()).isSameAs(saved.getLevelId());
    }

    @Test
    public void testSaveWithNullId() {
        when(repository.save(any(EmployeeDao.class))).thenAnswer(i -> i.getArguments()[0]);
        EmployeeDto toSave = new EmployeeDto();
        assertNull(toSave.getId());
        EmployeeDto saved = employeeService.save(toSave);
        assertNotNull(saved.getId());
    }

    @Test
    public void testFindAll() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setId(id1);
        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setId(id2);
        List<EmployeeDto> employeeDtos = List.of(employeeDto1, employeeDto2);

        EmployeeDao employeeDao1 = new EmployeeDao();
        employeeDao1.setId(id1);
        employeeDao1.setPosition(new PositionDao());
        employeeDao1.setLevel(new EmployeeLevelDao());
        EmployeeDao employeeDao2 = new EmployeeDao();
        employeeDao2.setId(id2);
        employeeDao2.setPosition(new PositionDao());
        employeeDao2.setLevel(new EmployeeLevelDao());
        List<EmployeeDao> employeeDaos = List.of(employeeDao1, employeeDao2);

        when(repository.findAll()).thenReturn(employeeDaos);

        List<EmployeeDto> findAll = employeeService.findAll();

        assertThat(findAll.get(0).getId()).isSameAs(employeeDtos.get(0).getId());
        assertThat(findAll.get(1).getId()).isSameAs(employeeDtos.get(1).getId());
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.setId(id);
        employeeDao.setPosition(new PositionDao());
        employeeDao.setLevel(new EmployeeLevelDao());

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(employeeDao));

        EmployeeDto findById = employeeService.findById(id);
        assertThat(findById.getId()).isSameAs(id);

    }

    @Test
    public void testFindByThrowNoSuchEntityFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> employeeService.findById(id));
        assertEquals("Employee with id " + id + " not found!", exception.getMessage());
    }

    @Test
    public void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(any(UUID.class));
        employeeService.deleteById(id);
        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    public void testFindAllEmails(){
        List<String> emails = List.of("email1", "email2");
        when(repository.findAllEmails()).thenReturn(emails);
        assertThat(emails).isSameAs(employeeService.findAllEmails());
    }
}
