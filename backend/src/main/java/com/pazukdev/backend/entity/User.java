package com.pazukdev.backend.entity;

import com.pazukdev.backend.constant.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    @Column(name = "alias")
    private String alias;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "enabled")
    private boolean enabled = true;


    public void setAlias(String alias) {
        this.alias = getName();
    }

}
