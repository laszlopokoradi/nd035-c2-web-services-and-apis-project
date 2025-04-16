package com.udacity.boogle.maps.service;

import com.udacity.boogle.maps.domain.*;
import com.udacity.boogle.maps.repository.*;
import org.springframework.stereotype.*;

@Service
public class LocationService {
    // This class is responsible for handling location-related operations.
    // It interacts with the LocationRepository to perform CRUD operations on Location entities.

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location getLocation(Double lat, Double lon) {
        return locationRepository.findByCoordinates(lat, lon).orElse(null);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
