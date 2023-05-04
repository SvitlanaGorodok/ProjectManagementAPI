package api.projectmanagement.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Table(name = "employees")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "first_name", nullable = false, length = 20)
    String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    String lastName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    PositionDao position;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "level_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    EmployeeLevelDao level;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "employees")
    List<ProjectDao> projects;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position=" + position.getName() +
                ", level=" + level.getName() +
                '}';
    }
}
