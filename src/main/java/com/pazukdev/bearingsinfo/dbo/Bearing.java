package com.pazukdev.bearingsinfo.dbo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@Entity
@Table(name = "BEARINGS_INFO")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Bearing extends AbstractEntity {

    @Column(name = "UNIT")
    private String unit;
    @Column(name = "QUANTITY")
    private Integer quantity;
}
