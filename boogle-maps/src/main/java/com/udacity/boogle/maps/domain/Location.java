package com.udacity.boogle.maps.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;

@Entity
@Accessors(chain = true)
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String address;
}
