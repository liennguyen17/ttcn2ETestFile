package com.example.ttcn2etest.model.etity;


import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "service")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    public enum TypeService{
        EDUCATION_PROGRAM, REVIEW_LESSON, COURSE
    }

    public enum Learn{
        ONLINE, OFFLINE, ONLINE_AND_OFFLINE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 2000)
    private String description;
    @NotBlank
    @Size(max = 300)
    @Column(name = "study_goals")
    private String studyGoals;

    @Column(name = "schedule")
    private String schedule;
    @Column(name = "number_teaching_sessions")
    private int numberTeachingSessions;
    @Column(name = "curriculum")
    private String curriculum;
    @Column(name = "learn_online_or_offline")
    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    private Learn learnOnlineOrOffline;
    @Column(name = "course_price", precision = 10, scale = 2)
    private BigDecimal coursePrice;
    @Column(name = "request_students")
    private String requestStudents;
    @Column(name = "type_service")
    @Enumerated(EnumType.STRING)
    private TypeService typeOfService;       //là dịch vụ chương trình Anh ngữ, Khóa học trong CT hay là lịch ôn tập
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "update_date")
    private Timestamp updateDate;


//    @ManyToOne
//    @JoinColumn(name = "teacher_id")
//    private Teacher teacher;
//
//    @OneToMany(mappedBy = "service")
//    private Set<Course> courses;
//
//    @ManyToMany+
//    @JoinTable(
//            name = "user_service",
//            joinColumns = @JoinColumn(
//                    name = "service_id", referencedColumnName = "service_id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "user_id"))
//    private Collection<User> users;


}