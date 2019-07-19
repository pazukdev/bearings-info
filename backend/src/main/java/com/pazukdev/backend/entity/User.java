package com.pazukdev.backend.entity;

import com.pazukdev.backend.constant.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
public class User extends AbstractEntity {

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

}
