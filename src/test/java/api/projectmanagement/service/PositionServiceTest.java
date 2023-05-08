package api.projectmanagement.service;

import api.projectmanagement.model.converter.PositionConverter;
import api.projectmanagement.model.dao.PositionDao;
import api.projectmanagement.model.dto.PositionDto;
import api.projectmanagement.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PositionServiceTest {
    @Mock
    private PositionRepository repository;

    @Mock
    private PositionConverter converter;

    @InjectMocks
    private PositionService positionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        UUID id = UUID.randomUUID();
        PositionDto positionDto = new PositionDto();
        positionDto.setId(id);
        PositionDao positionDao = new PositionDao();
        positionDao.setId(id);
        when(converter.toDao(any(PositionDto.class))).thenReturn(positionDao);
        when(converter.toDto(any(PositionDao.class))).thenReturn(positionDto);
        when(repository.save(any(PositionDao.class))).thenReturn(positionDao);
        PositionDto saved = positionService.save(positionDto);
        assertThat(positionDto.getId()).isSameAs(saved.getId());
    }

    @Test
    public void testSaveWithNullId() {
        UUID id = UUID.randomUUID();
        PositionDto positionDtoWithoutId = new PositionDto();
        PositionDto positionDtoWithId = new PositionDto();
        positionDtoWithId.setId(id);
        PositionDao positionDao = new PositionDao();
        positionDao.setId(id);
        when(converter.toDao(any(PositionDto.class))).thenReturn(positionDao);
        when(converter.toDto(any(PositionDao.class))).thenReturn(positionDtoWithId);
        when(repository.save(any(PositionDao.class))).thenReturn(positionDao);
        PositionDto saved = positionService.save(positionDtoWithoutId);
        assertThat(positionDtoWithId.getId()).isSameAs(saved.getId());
    }

    @Test
    public void testFindAll() {
        UUID id = UUID.randomUUID();

        PositionDto positionDto = new PositionDto();
        positionDto.setId(id);
        List<PositionDto> positionDtos = List.of(positionDto);

        PositionDao positionDao = new PositionDao();
        positionDao.setId(id);
        List<PositionDao> positionDaos = List.of(positionDao);

        when(repository.findAll()).thenReturn(positionDaos);
        when(converter.toDto(any(PositionDao.class))).thenReturn(positionDto);

        List<PositionDto> all = positionService.findAll();
        assertThat(all.get(0).getId()).isSameAs(positionDtos.get(0).getId());
    }
}


