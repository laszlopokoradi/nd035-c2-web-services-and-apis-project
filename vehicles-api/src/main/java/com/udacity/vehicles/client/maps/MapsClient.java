package com.udacity.vehicles.client.maps;

import com.udacity.vehicles.domain.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.cloud.client.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

/**
 * Implements a class to interface with the Maps Client for location data.
 */
@Component
public class MapsClient {

    private static final Logger log = LoggerFactory.getLogger(MapsClient.class);

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final ModelMapper mapper;

    public MapsClient(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder, ModelMapper mapper) {
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
        this.mapper = mapper;
    }

    /**
     * Gets an address from the Maps client, given latitude and longitude.
     *
     * @param location An object containing "lat" and "lon" of location
     * @return An updated location including street, city, state and zip,
     * or an exception message noting the Maps service is down
     */
    public Location getAddress(Location location) {
        ServiceInstance boogleMapsService = discoveryClient.getInstances("boogle-maps").getFirst();

        try {
            Address address = this.restClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(boogleMapsService.getUri().toString() + "/maps/")
                            .queryParam("lat", location.getLat())
                            .queryParam("lon", location.getLon())
                            .build()
                    )
                    .retrieve().body(Address.class);

            mapper.map(Objects.requireNonNull(address), location);

            return location;
        } catch (Exception e) {
            log.warn("Map service is down");
            return location;
        }
    }
}
