package com.pazukdev.backend.entity.product;

import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.entity.Manufacturer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractProduct extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @Column(name = "production_start_year")
    private Integer productionStartYear;
    @Column(name = "production_stop_year")
    private Integer productionStopYear;

}
