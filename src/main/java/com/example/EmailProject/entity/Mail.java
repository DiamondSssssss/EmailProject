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
    @Column(name = "Email",nullable = false)
    private String email;
}
