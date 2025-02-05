package com.example.EmailProject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Mail")
@Data
@Builder

public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MailId")
    private UUID mailId;

    @Column(name = "Email",nullable = false)
    private String email;

    @Column(name = "Title", nullable = false, unique = true)
    private String title;

    @Column(name = "Description",nullable = false)
    private String description;

}
