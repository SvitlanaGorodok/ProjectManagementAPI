package api.projectmanagement.service;

import api.projectmanagement.exception.EntityNotFoundException;
import api.projectmanagement.model.converter.EmployeeLevelConverter;
import api.projectmanagement.model.dao.EmployeeLevelDao;
import api.projectmanagement.model.dto.EmployeeLevelDto;
import api.projectmanagement.repository.EmployeeLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeLevelService implements CRUDService<EmployeeLevelDto>{
    private final EmployeeLevelConverter converter;
    private final EmployeeLevelRepository repository;
    @Override
    public EmployeeLevelDto save(EmployeeLevelDto levelDto) {
        if(levelDto.getId() == null){
            levelDto.setId(UUID.randomUUID());
        }
        EmployeeLevelDao saved = repository.save(converter.toDao(levelDto));
        return converter.toDto(saved);
    }

    @Override
    public List<EmployeeLevelDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeLevelDto findById(UUID id) {
        EmployeeLevelDao levelDao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee level with id " + id + " not found!"));
        return converter.toDto(levelDao);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
