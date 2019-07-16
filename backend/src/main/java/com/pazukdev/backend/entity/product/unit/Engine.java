package com.pazukdev.backend.entity.product.unit;

import com.pazukdev.backend.entity.product.Bearing;
import com.pazukdev.backend.entity.product.Oil;
import com.pazukdev.backend.entity.product.SparkPlug;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "engine")
public class Engine extends Unit {

    @Column(name = "power_hp")
    private Integer powerHp;
    @Column(name = "torque_nm")
    private Integer torqueNm;
    @Column(name = "speed_rpm")
    private Integer speedRpm;
    @ManyToOne
    @JoinColumn(name = "spark_plug_id")
    private SparkPlug sparkPlug;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany
    @JoinTable(
            name = "engine_bearing",
            joinColumns = @JoinColumn(name = "engine_id"),
            inverseJoinColumns = @JoinColumn(name = "bearing_id")
    )
    private Set<Bearing> bearings = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany
    @JoinTable(
            name = "engine_oil",
            joinColumns = @JoinColumn(name = "engine_id"),
            inverseJoinColumns = @JoinColumn(name = "oil_id")
    )
    private Set<Oil> oils = new HashSet<>();

}















