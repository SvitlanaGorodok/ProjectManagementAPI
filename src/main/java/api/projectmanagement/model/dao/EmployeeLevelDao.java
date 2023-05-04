package api.projectmanagement.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "employee_levels")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLevelDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false, length = 20, unique = true)
    String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "level")
    List<EmployeeDao> employees;
}
