package com.pazukdev.backend.entity.product;

import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
public class Product extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;
    @Column(name = "production_start_year")
    private Integer productionStartYear;
    @Column(name = "production_stop_year")
    private Integer productionStopYear;

}
