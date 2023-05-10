package api.projectmanagement.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindProjectParam {
    String name;
    String startDateFrom;
    String startDateTo;
    String endDateFrom;
    String endDateTo;
}
