package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.*;
import org.springframework.stereotype.*;

import java.math.*;
import java.util.concurrent.*;

/**
 * Implements the pricing service to get prices for each vehicle.
 */
@Service
public class PricingService {
    private final PriceRepository priceRepository;

    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD", "MXN"};

    public PricingService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
     *
     * @param vehicleId ID number of the vehicle the price is requested for.
     * @return price of the requested vehicle
     * @throws PriceException vehicleID was not found
     */
    public Price getPrice(Long vehicleId) throws PriceException {
        if (vehicleId == null || vehicleId < 0) {
            throw new PriceException("Invalid vehicle ID");
        }

        return this.priceRepository.findByVehicleId(vehicleId)
                .orElseGet(()-> {
                    Price price = new Price()
                            .setCurrency(randomCurrency())
                            .setAmount(randomPrice())
                            .setVehicleId(vehicleId);
                    return this.priceRepository.save(price);
                });
    }

    private String randomCurrency() {
        return CURRENCIES[ThreadLocalRandom.current().nextInt(CURRENCIES.length)];
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     *
     * @return random price for a vehicle
     */
    private BigDecimal randomPrice() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(BigDecimal.valueOf(50000d)).setScale(2, RoundingMode.HALF_UP);
    }

}