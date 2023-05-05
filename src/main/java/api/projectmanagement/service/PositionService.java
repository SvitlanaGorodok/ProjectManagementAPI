package api.projectmanagement.service;

import api.projectmanagement.exception.EntityNotFoundException;
import api.projectmanagement.model.converter.PositionConverter;
import api.projectmanagement.model.dao.PositionDao;
import api.projectmanagement.model.dto.PositionDto;
import api.projectmanagement.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService implements CRUDService<PositionDto> {
    private final PositionRepository repository;
    private final PositionConverter converter;

    @Override
    public PositionDto save(PositionDto positionDto) {
        if(positionDto.getId() == null){
            positionDto.setId(UUID.randomUUID());
        }
        PositionDao saved = repository.save(converter.toDao(positionDto));
        return converter.toDto(saved);
    }

    @Override
    public List<PositionDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PositionDto findById(UUID id) {
        PositionDao positionDao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position with id " + id + " not found!"));
        return converter.toDto(positionDao);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
