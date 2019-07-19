package com.pazukdev.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "verification_token")
public class VerificationToken extends AbstractEntity {

    private String token;
    private LocalDate expiryDate;

}
