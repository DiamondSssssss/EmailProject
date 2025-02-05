package com.example.EmailProject.dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private UUID UserId;
    private String Name;
    private String Email;
    private String Message;
    private String OfWebsite;
}