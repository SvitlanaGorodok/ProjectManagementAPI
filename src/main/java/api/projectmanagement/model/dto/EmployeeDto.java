package api.projectmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    UUID id;
    String firstName;
    String lastName;
    String email;
    PositionDto position;
    EmployeeLevelDto level;
}
