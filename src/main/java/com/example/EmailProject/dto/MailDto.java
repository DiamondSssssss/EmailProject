package com.example.EmailProject.dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class MailDto {
    private UUID MailId;
    private String Email;
    private String Title;
    private String Description;
}