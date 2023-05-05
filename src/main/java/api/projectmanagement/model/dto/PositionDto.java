package api.projectmanagement.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PositionDto {
    UUID id;
    String name;
}

