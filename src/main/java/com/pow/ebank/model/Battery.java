package com.pow.ebank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="battery")
public class Battery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battery_id")
    private Long batteryId;

    @Column(name = "battery_name")
    private String name;

    @Column(name = "watt_capacity")
    private Integer wattCapacity;

    @ManyToOne
    @JoinColumn(name="post_code",nullable = false)
    private Location location;


}
