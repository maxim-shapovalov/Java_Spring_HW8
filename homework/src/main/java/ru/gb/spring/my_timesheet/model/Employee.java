package ru.gb.spring.my_timesheet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String department;
//    @ManyToMany(mappedBy = "employees")
////    @JoinTable(
////            name = "employee_project",
////            joinColumns = @JoinColumn(name ="employee_id"),
////            inverseJoinColumns = @JoinColumn(name = "project_id")
////    )
//    private List<Project> projects;
    @OneToMany(mappedBy = "employee")
    private Set<Timesheet> timesheets;
}
