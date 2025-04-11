package com.udacity.vehicles.client.prices;

import lombok.*;
import lombok.experimental.*;

import java.math.BigDecimal;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Accessors(chain = true)
@Getter
@Setter
public class Price {
    private Long id;

    private String currency;

    private BigDecimal amount;

    private Long vehicleId;
}

