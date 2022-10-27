package com.example.bookreviewclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Location implements Serializable {
    @Id
    @Column(name="LOCATION_ID")
    @SequenceGenerator(name = "LOCATION_ID_GENERATOR", sequenceName = "LOCATION_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_ID_GENERATOR")
    private Long locationId;

    @Column(name="LAT")
    private BigDecimal lat;
    @Column(name="LNG")
    private BigDecimal lng;

    public Location() {

    }
}
