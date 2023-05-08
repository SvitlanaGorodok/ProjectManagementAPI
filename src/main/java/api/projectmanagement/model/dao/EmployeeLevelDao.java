package api.projectmanagement.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "employee_levels")
@Entity
@Setter
@Getter
public class EmployeeLevelDao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "level")
    List<EmployeeDao> employees;
}
