package com.epam.izh.rd.online.autcion.entity;

import lombok.Getter;

import java.util.Objects;

/** Пользователь аукциона */
@Getter
public class User {

  private Long userId;
  private String billingAddress;
  private String fullName;
  private String login;
  private String password;

  public User(long userId, String billingAddress, String fullName, String login, String password) {
    this.userId = userId;
    this.billingAddress = billingAddress;
    this.fullName = fullName;
    this.login = login;
    this.password = password;
  }

  public User() {}

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setBillingAddress(String billingAddress) {
    this.billingAddress = billingAddress;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(getUserId(), user.getUserId())
        && Objects.equals(getBillingAddress(), user.getBillingAddress())
        && Objects.equals(getFullName(), user.getFullName())
        && Objects.equals(getLogin(), user.getLogin())
        && Objects.equals(getPassword(), user.getPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUserId(), getBillingAddress(), getFullName(), getLogin(), getPassword());
  }

  @Override
  public String toString() {
    return "User{"
        + "userId="
        + userId
        + ", billingAddress='"
        + billingAddress
        + '\''
        + ", fullName='"
        + fullName
        + '\''
        + ", login='"
        + login
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
