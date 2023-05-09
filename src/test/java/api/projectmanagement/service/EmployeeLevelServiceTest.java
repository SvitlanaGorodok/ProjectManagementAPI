package api.projectmanagement.service;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.model.converter.EmployeeLevelConverter;
import api.projectmanagement.model.dao.EmployeeLevelDao;
import api.projectmanagement.model.dto.EmployeeLevelDto;
import api.projectmanagement.repository.EmployeeLevelRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class EmployeeLevelServiceTest {
    @Mock
    private EmployeeLevelRepository repository;
    private AutoCloseable closeable;
    private EmployeeLevelService levelService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        levelService = new EmployeeLevelService(new EmployeeLevelConverter(), repository);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void testSaveAndConverter() {
        UUID id = UUID.randomUUID();
        EmployeeLevelDto levelDto = new EmployeeLevelDto();
        levelDto.setId(id);
        levelDto.setName("name");
        when(repository.save(any(EmployeeLevelDao.class))).thenAnswer(i -> i.getArguments()[0]);
        EmployeeLevelDto saved = levelService.save(levelDto);
        assertThat(levelDto.getId()).isSameAs(saved.getId());
        assertThat(levelDto.getName()).isSameAs(saved.getName());
    }

    @Test
    public void testSaveWithNullId() {
        when(repository.save(any(EmployeeLevelDao.class))).thenAnswer(i -> i.getArguments()[0]);
        EmployeeLevelDto toSave = new EmployeeLevelDto();
        assertNull(toSave.getId());
        EmployeeLevelDto saved = levelService.save(toSave);
        assertNotNull(saved.getId());
    }

    @Test
    public void testFindAll() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        EmployeeLevelDto levelDto1 = new EmployeeLevelDto();
        levelDto1.setId(id1);
        EmployeeLevelDto levelDto2 = new EmployeeLevelDto();
        levelDto2.setId(id2);
        List<EmployeeLevelDto> levelDtos = List.of(levelDto1, levelDto2);

        EmployeeLevelDao levelDao1 = new EmployeeLevelDao();
        levelDao1.setId(id1);
        EmployeeLevelDao levelDao2 = new EmployeeLevelDao();
        levelDao2.setId(id2);
        List<EmployeeLevelDao> levelDaos = List.of(levelDao1, levelDao2);

        when(repository.findAll()).thenReturn(levelDaos);

        List<EmployeeLevelDto> findAll = levelService.findAll();

        assertThat(findAll.get(0).getId()).isSameAs(levelDtos.get(0).getId());
        assertThat(findAll.get(1).getId()).isSameAs(levelDtos.get(1).getId());
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        EmployeeLevelDao levelDao = new EmployeeLevelDao();
        levelDao.setId(id);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(levelDao));

        EmployeeLevelDto findById = levelService.findById(id);
        assertThat(findById.getId()).isSameAs(id);

    }

    @Test
    public void testFindByThrowNoSuchEntityFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> levelService.findById(id));
        assertEquals("Employee level with id " + id + " not found!", exception.getMessage());
    }

    @Test
    public void testDeleteById(){
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(any(UUID.class));
        levelService.deleteById(id);
        verify(repository,times(1)).deleteById(any(UUID.class));
    }
}
