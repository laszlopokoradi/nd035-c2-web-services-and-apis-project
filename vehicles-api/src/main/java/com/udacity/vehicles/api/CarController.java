package com.udacity.vehicles.api;


import com.udacity.vehicles.domain.car.*;
import com.udacity.vehicles.service.*;
import jakarta.validation.*;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;

    CarController(CarService carService, CarResourceAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }

    /**
     * Creates a list to store any vehicles.
     *
     * @return list of vehicles
     */
    @GetMapping
    CollectionModel<EntityModel<Car>> list() {
        List<EntityModel<Car>> resources = carService.list().stream().map(assembler::toModel).toList();
        return CollectionModel.of(resources, linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     *
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping("/{id}")
    EntityModel<Car> get(@PathVariable Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be null");
        }

        Car car = this.carService.findById(id);

        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }

        return assembler.toModel(car);
    }

    /**
     * Posts information to create a new vehicle in the system.
     *
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     */
    @PostMapping
    ResponseEntity<EntityModel<Car>> post(@Valid @RequestBody Car car) {
        if (car == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car cannot be null");
        }

        Car newCar = this.carService.save(car);

        if (newCar == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Car could not be created");
        }

        EntityModel<Car> resource = assembler.toModel(newCar);

        return ResponseEntity.created(linkTo(methodOn(CarController.class).get(newCar.getId())).toUri()).body(resource);
    }

    /**
     * Updates the information of a vehicle in the system.
     *
     * @param id  The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Car>> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        if (id == null || car == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID and Car cannot be null");
        }

        if (car.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car ID cannot be null, use POST to create a new car");
        }

        if (!id.equals(car.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and ID in body do not match");
        }

        if (this.carService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }

        Car updatedCar = this.carService.save(car);

        if (updatedCar == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Car could not be updated");
        }

        EntityModel<Car> resource = assembler.toModel(updatedCar);
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     *
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseEntity<Car>> delete(@PathVariable Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be null");
        }

        this.carService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
