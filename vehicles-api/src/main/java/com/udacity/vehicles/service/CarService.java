package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.*;
import com.udacity.vehicles.client.prices.*;
import com.udacity.vehicles.domain.*;
import com.udacity.vehicles.domain.car.*;
import org.springframework.stereotype.*;

import java.util.*;

;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient mapsClient;
    private final PricingClient pricingClient;

    public CarService(CarRepository repository, MapsClient mapsClient, PricingClient pricingClient) {
        this.repository = repository;
        this.mapsClient = mapsClient;
        this.pricingClient = pricingClient;
    }

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Car car = this.repository.findById(id)
                .orElseThrow(CarNotFoundException::new);

        String price = this.pricingClient.getPrice(car.getId());
        car.setPrice(price);

        Location location = car.getLocation();
        car.setLocation(location);
        // Note: The Location class file also uses @transient for the address, meaning the Maps service needs to be called each time for the address.

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        this.repository.findById(id)
                .orElseThrow(CarNotFoundException::new);

        this.pricingClient.deletePriceOfCar(id);

        this.repository.deleteById(id);
    }
}
