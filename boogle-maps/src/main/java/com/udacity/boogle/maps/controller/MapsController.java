package com.udacity.boogle.maps.controller;

import com.udacity.boogle.maps.domain.*;
import com.udacity.boogle.maps.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maps")
public class MapsController {
    private final LocationService locationService;
    private final AddressService addressService;

    public MapsController(LocationService locationService, AddressService addressService) {
        this.locationService = locationService;
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public Address get(@RequestParam Double lat, @RequestParam Double lon) {
        Location location = this.locationService.getLocation(lat, lon);
        String address;

        if (location == null) {
            location = new Location();
            location.setLatitude(lat)
                    .setLongitude(lon);
        }

        if (location.getAddress() == null) {
            address = this.addressService.getRandomString();
            location.setAddress(address);
            location = this.locationService.saveLocation(location);
        }

        address = location.getAddress();

        return this.addressService.toAddress(address);
    }
}
