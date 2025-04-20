package com.udacity.pricing.api;

import com.udacity.pricing.domain.price.*;
import com.udacity.pricing.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
public class PricingController {
    private final PricingService pricingService;

    /**
     * Constructor for the pricing controller.
     *
     * @param pricingService service to get prices from
     */
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }


    /**
     * Gets the price for a requested vehicle.
     *
     * @param vehicleId ID number of the vehicle for which the price is requested
     * @return price of the vehicle, or error that it was not found.
     */
    @GetMapping("/services/priceOf")
    public Price get(@RequestParam Long vehicleId) {
        try {
            return this.pricingService.getPrice(vehicleId);
        } catch (PriceException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Price Not Found", ex);
        }

    }
}