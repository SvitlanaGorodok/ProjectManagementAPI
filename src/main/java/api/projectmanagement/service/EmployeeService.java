package api.projectmanagement.service;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import api.projectmanagement.model.converter.EmployeeConverter;
import api.projectmanagement.model.dao.EmployeeDao;
import api.projectmanagement.model.dto.EmployeeDto;
import api.projectmanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements CRUDService<EmployeeDto> {
    private final EmployeeConverter converter;
    private final EmployeeRepository repository;

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        if (employeeDto.getId() == null) {
            employeeDto.setId(UUID.randomUUID());
        }
        EmployeeDao saved = repository.save(converter.toDao(employeeDto));
        return converter.toDto(saved);
    }

    @Override
    public List<EmployeeDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findById(UUID id) {
        EmployeeDao employeeDao = repository.findById(id)
                .orElseThrow(() -> new NoSuchEntityFoundException("Employee with id " + id + " not found!"));
        return converter.toDto(employeeDao);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<String> findAllEmails(){
        return repository.findAllEmails();
    }

    public List<EmployeeDto> findByParameters(EmployeeDto employeeDto){
        String firstName = sqlFormat(employeeDto.getFirstName());
        String lastName = sqlFormat(employeeDto.getLastName());
        String email = sqlFormat(employeeDto.getEmail());
        List<EmployeeDao> employees = repository.findByParameters(
                firstName, lastName, email,
                employeeDto.getPositionId(), employeeDto.getLevelId());
        return employees.stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    private String sqlFormat(String text){
        if(text == null || text.isEmpty()){
            return "";
        }
        return "%" + text.toLowerCase() + "%";
    }

}
