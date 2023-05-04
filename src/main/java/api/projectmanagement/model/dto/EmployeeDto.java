package api.projectmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    Integer id;
    String firstName;
    String lastName;
    PositionDto position;
    EmployeeLevelDto level;
}
