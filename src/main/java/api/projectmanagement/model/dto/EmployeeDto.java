package api.projectmanagement.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class EmployeeDto {
    UUID id;
    String firstName;
    String lastName;
    String email;
    UUID positionId;
    UUID levelId;
    String positionDetails;
}
