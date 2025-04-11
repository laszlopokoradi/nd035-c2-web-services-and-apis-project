package com.udacity.pricing.domain.price;

import lombok.*;
import lombok.experimental.*;
import jakarta.persistence.*;

import java.math.*;


/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity
@Accessors(chain = true)
@Getter
@Setter
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, unique = true)
    private Long vehicleId;
}
