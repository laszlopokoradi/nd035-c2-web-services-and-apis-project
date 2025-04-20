package com.udacity.vehicles.client.maps;

import lombok.*;
import lombok.experimental.*;

/**
 * Declares a class to store an address, city, state and zip code.
 */

@Accessors(chain = true)
@Getter
@Setter
public class Address {

    private String streetAndNumber;
    private String city;
    private String state;
    private String zip;
}
