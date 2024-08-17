//package ru.gb.spring.my_timesheet.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//@Data
//@Entity
//@Table(name = "employee_project")
//public class EmployeeProject {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @EqualsAndHashCode.Include
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private Project project;
//}
