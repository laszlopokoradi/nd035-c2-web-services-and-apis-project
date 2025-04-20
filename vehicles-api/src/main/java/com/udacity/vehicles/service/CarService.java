package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.*;
import com.udacity.vehicles.client.prices.*;
import com.udacity.vehicles.domain.*;
import com.udacity.vehicles.domain.car.*;
import org.springframework.stereotype.*;

import java.util.*;

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
        List<Car> allCars = repository.findAll();

        for (Car car : allCars) {
            collectPriceOf(car);
            collectAddressOf(car);
        }

        return allCars;
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Car car = this.repository.findById(id)
                .orElseThrow(CarNotFoundException::new);

        collectPriceOf(car);
        collectAddressOf(car);

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }

        Car savedCar = this.repository.save(car);

        this.collectPriceOf(savedCar);
        this.collectAddressOf(savedCar);

        return savedCar;
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        this.repository.findById(id)
                .orElseThrow(CarNotFoundException::new);

        this.pricingClient.deletePriceOfCar(id);

        this.repository.deleteById(id);
    }

    private void collectPriceOf(Car car) {
        if (car == null || car.getId() == null) {
            throw new IllegalArgumentException("Car or Car ID cannot be null");
        }

        String price = this.pricingClient.getPrice(car.getId());
        car.setPrice(price);
    }

    private void collectAddressOf(Car car) {
        if (car == null || car.getLocation() == null) {
            throw new IllegalArgumentException("Car or Car location cannot be null");
        }

        Location locationWithAddress = this.mapsClient.getAddress(car.getLocation());
        car.setLocation(locationWithAddress);
    }
}
