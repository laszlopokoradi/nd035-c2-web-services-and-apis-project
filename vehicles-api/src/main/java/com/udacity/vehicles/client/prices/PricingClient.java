package com.udacity.vehicles.client.prices;


import org.slf4j.*;
import org.springframework.cloud.client.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;


/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PricingClient {

    private static final Logger log = LoggerFactory.getLogger(PricingClient.class);

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public PricingClient(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
    }

    // In a real-world application we'll want to add some resilience
    // to this method with retries/CB/failover capabilities
    // We may also want to cache the results so we don't need to
    // do a request every time

    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     *
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle, error message that the vehicle ID is invalid, or note that the service is down.
     */
    public String getPrice(Long vehicleId) {
        ServiceInstance pricingService = discoveryClient.getInstances("pricing-service").getFirst();
        try {
            Price price = restClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(pricingService.getUri().toString() + "/api/price")
                            .queryParam("vehicleId", vehicleId)
                            .build()
                    )
                    .retrieve()
                    .body(Price.class);

            if (price == null) {
                log.error("Price not found for vehicle {}", vehicleId);
                throw new IllegalStateException("Price not found!");
            }

            return String.format("%s %s", price.getCurrency(), price.getAmount());

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }

    public void deletePriceOfCar(Long id) {
        ServiceInstance pricingService = discoveryClient.getInstances("pricing-service").get(0);
        try {
            restClient
                    .delete()
                    .uri(uriBuilder -> uriBuilder
                            .path(pricingService.getUri().toString() + "/api/price")
                            .queryParam("vehicleId", id)
                            .build()
                    )
                    .retrieve()
                    .body(Void.class);
        } catch (Exception e) {
            log.error("Unexpected error deleting price for vehicle {}", id, e);
        }
    }
}
