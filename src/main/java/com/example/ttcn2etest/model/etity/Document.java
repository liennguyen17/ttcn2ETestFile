package com.example.ttcn2etest.model.etity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "document")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    public enum Status{
        FREE, NO_FREE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 200)
    private String name;
    @NotBlank
    @Size(max = 5000)
    private String content;

    private String file;
    @Size(max = 500)
    private String image;
    @Enumerated(EnumType.STRING)
    private Status status;  //tai lieu free hay khong free
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "update_date")
    private Timestamp updateDate;
}