package api.projectmanagement.service;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.model.converter.PositionConverter;
import api.projectmanagement.model.dao.PositionDao;
import api.projectmanagement.model.dto.PositionDto;
import api.projectmanagement.repository.PositionRepository;
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

public class PositionServiceTest {
    @Mock
    private PositionRepository repository;
    private AutoCloseable closeable;
    private PositionService positionService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        positionService = new PositionService(repository, new PositionConverter());
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void testSaveAndConverter() {
        UUID id = UUID.randomUUID();
        PositionDto positionDto = new PositionDto();
        positionDto.setId(id);
        positionDto.setName("name");
        when(repository.save(any(PositionDao.class))).thenAnswer(i -> i.getArguments()[0]);
        PositionDto saved = positionService.save(positionDto);
        assertThat(positionDto.getId()).isSameAs(saved.getId());
        assertThat(positionDto.getName()).isSameAs(saved.getName());
    }

    @Test
    public void testSaveWithNullId() {
        when(repository.save(any(PositionDao.class))).thenAnswer(i -> i.getArguments()[0]);
        PositionDto toSave = new PositionDto();
        assertNull(toSave.getId());
        PositionDto saved = positionService.save(toSave);
        assertNotNull(saved.getId());
    }

    @Test
    public void testFindAll() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        PositionDto positionDto1 = new PositionDto();
        positionDto1.setId(id1);
        PositionDto positionDto2 = new PositionDto();
        positionDto2.setId(id2);
        List<PositionDto> positionDtos = List.of(positionDto1, positionDto2);

        PositionDao positionDao1 = new PositionDao();
        positionDao1.setId(id1);
        PositionDao positionDao2 = new PositionDao();
        positionDao2.setId(id2);
        List<PositionDao> positionDaos = List.of(positionDao1, positionDao2);

        when(repository.findAll()).thenReturn(positionDaos);

        List<PositionDto> findAll = positionService.findAll();

        assertThat(findAll.get(0).getId()).isSameAs(positionDtos.get(0).getId());
        assertThat(findAll.get(1).getId()).isSameAs(positionDtos.get(1).getId());
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        PositionDao positionDao = new PositionDao();
        positionDao.setId(id);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(positionDao));

        PositionDto findById = positionService.findById(id);
        assertThat(findById.getId()).isSameAs(id);

    }

    @Test
    public void testFindByThrowNoSuchEntityFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> positionService.findById(id));
        assertEquals("Position with id " + id + " not found!", exception.getMessage());
    }

    @Test
    public void testDeleteById(){
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(any(UUID.class));
        positionService.deleteById(id);
        verify(repository,times(1)).deleteById(any(UUID.class));
    }
}


