package api.projectmanagement.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ProjectDto {
    UUID id;
    String name;
    Date startDate;
    Date endDate;
    List<UUID> employeeIds;
    List<String> projectsDetails;
}
