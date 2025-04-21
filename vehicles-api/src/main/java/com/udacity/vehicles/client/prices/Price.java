package com.udacity.vehicles.client.prices;

import java.math.BigDecimal;

public class Price {
    private Long id;
    private String currency;
    private BigDecimal amount;
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

