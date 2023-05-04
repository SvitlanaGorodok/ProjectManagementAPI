package api.projectmanagement.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table(name = "employeelevels")
@Entity
@Setter
@Getter
public class EmployeeLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
}
