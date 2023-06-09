package api.projectmanagement.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "projects")
@Entity
@Setter
@Getter
public class ProjectDao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    String name;

    @Column(name = "start_date", nullable = false)
    Date startDate;

    @Column(name = "end_date", nullable = false)
    Date endDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "projects_employees_relation",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<EmployeeDao> employees;
}
