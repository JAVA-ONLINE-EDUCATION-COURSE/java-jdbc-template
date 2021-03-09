package com.epam.izh.rd.online.autcion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Пользователь аукциона
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long userId;
    private String billingAddress;
    private String fullName;
    private String login;
    private String password;
}
