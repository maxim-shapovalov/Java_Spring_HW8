package ru.gb.spring.my_timesheet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Описание структуры json-ответа на REST-запросы.
 * Т.е. запросы, ответ на которые - JSON.
 */
// эта аннотация ломбок позволяет не прописывать геттеры и сеттеры, они идут вместе с ней
@Data
@Entity
@Table(name = "timesheet")
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;
//    private Long projectId;
//    private Long employeeId;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private Integer minutes;
//    private LocalDateTime createdAt;
    private LocalDate createdAt;
}
