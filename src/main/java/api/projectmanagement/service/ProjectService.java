package api.projectmanagement.service;

import api.projectmanagement.exception.EntityNotFoundException;
import api.projectmanagement.model.converter.ProjectConverter;
import api.projectmanagement.model.dao.ProjectDao;
import api.projectmanagement.model.dto.ProjectDto;
import api.projectmanagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService implements CRUDService<ProjectDto>{
    private final ProjectConverter converter;
    private final ProjectRepository repository;
    @Override
    public ProjectDto save(ProjectDto projectDto) {
        if(projectDto.getId() == null){
            projectDto.setId(UUID.randomUUID());
        }
        ProjectDao saved = repository.save(converter.toDao(projectDto));
        return converter.toDto(saved);
    }

    @Override
    public List<ProjectDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto findById(UUID id) {
        ProjectDao projectDao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with id " + id + " not found!"));
        return converter.toDto(projectDao);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<String> findAllNames(){
        return repository.findAllNames();
    }
}
