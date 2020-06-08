package com.epam.izh.rd.online.autcion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Пользователь аукциона
 */

public class User {
    private Long userId;
    private String billingAddress;
    private String fullName;
    private String login;
    private String password;

    public User(Long userId, String billingAddress, String fullName, String login, String password) {
        this.userId = userId;
        this.billingAddress = billingAddress;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(billingAddress, user.billingAddress) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, billingAddress, fullName, login, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", billingAddress='" + billingAddress + '\'' +
                ", fullName='" + fullName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
