package com.udacity.pricing.domain.price;

import jakarta.persistence.*;

import java.math.*;


/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity
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

    public Long getId() {
        return id;
    }

    public Price setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Price setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Price setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Price setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }
}
