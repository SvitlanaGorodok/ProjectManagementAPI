package api.projectmanagement.service;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.model.converter.ProjectConverter;
import api.projectmanagement.model.dao.ProjectDao;
import api.projectmanagement.model.dto.FindProjectParam;
import api.projectmanagement.model.dto.ProjectDto;
import api.projectmanagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
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
        ProjectDao projectDao;
        if(projectDto.getId() == null){
            projectDto.setId(UUID.randomUUID());
        }
        try {
            projectDao = repository.save(converter.toDao(projectDto));
        } catch (JpaObjectRetrievalFailureException e){
            throw new NoSuchEntityFoundException("Couldn't create project! Employees not found!");
        }
        return converter.toDto(projectDao);
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
                .orElseThrow(() -> new NoSuchEntityFoundException("Project with id " + id + " not found!"));
        return converter.toDto(projectDao);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<String> findAllNames(){
        return repository.findAllNames();
    }

    public List<ProjectDto> findByParameters(FindProjectParam projectParam){
        String name = sqlTextFormat(projectParam.getName());
        Date startDateFrom = sqlDateFormat(projectParam.getStartDateFrom());
        Date startDateTo = sqlDateFormat(projectParam.getStartDateTo());
        Date endDateFrom = sqlDateFormat(projectParam.getEndDateFrom());
        Date endDateTo = sqlDateFormat(projectParam.getEndDateTo());
        List<ProjectDao> employees = repository.findByParameters(
                name, startDateFrom, startDateTo,
                endDateFrom, endDateTo);
        return employees.stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    private String sqlTextFormat(String text){
        if(text == null || text.isEmpty()){
            return "%%";
        }
        return "%" + text.toLowerCase() + "%";
    }

    private Date sqlDateFormat(String text){
        if(text == null || text.isEmpty()){
            return null;
        }
        return Date.valueOf(text);
    }
}
