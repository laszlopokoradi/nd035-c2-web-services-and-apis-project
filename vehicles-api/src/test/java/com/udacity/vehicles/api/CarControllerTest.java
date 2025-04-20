package com.udacity.vehicles.api;


import com.udacity.vehicles.client.maps.*;
import com.udacity.vehicles.client.prices.*;
import com.udacity.vehicles.domain.*;
import com.udacity.vehicles.domain.car.*;
import com.udacity.vehicles.domain.manufacturer.*;
import com.udacity.vehicles.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.json.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.json.*;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.*;
import org.springframework.test.web.servlet.*;

import java.net.*;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Implements testing of the CarController class.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockitoBean
    private CarService carService;

    @MockitoBean
    private PricingClient priceClient;

    @MockitoBean
    private MapsClient mapsClient;

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @BeforeEach
    void setup() {
        Car car = getCar();
        car.setId(1L);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));
    }

    /**
     * Tests for successful creation of new car in the system
     *
     * @throws Exception when car creation fails in the system
     */
    @Test
    void createCar()
            throws Exception {
        Car car = getCar();
        mvc.perform(post(new URI("/cars")).content(json.write(car)
                                .getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     *
     * @throws Exception if the read operation of the vehicle list fails
     */
    @Test
    void listCars()
            throws Exception {
        Car car = getCar();

        mvc.perform(post(new URI("/cars")).content(json.write(car)
                                .getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(get(new URI("/cars")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.carList[0].details.manufacturer.name").value("Chevrolet"))
                .andExpect(jsonPath("$._embedded.carList[0].details.model").value("Impala"))
                .andExpect(jsonPath("$._embedded.carList[0].condition").value(Condition.USED.toString()))
                .andExpect(jsonPath("$._embedded.carList[0].location.lat").value(40.730610))
                .andExpect(jsonPath("$._embedded.carList[0].location.lon").value(-73.935242));
    }

    /**
     * Tests the read operation for a single car by ID.
     *
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    void findCar()
            throws Exception {

        mvc.perform(get(new URI("/cars/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details.manufacturer.name").value("Chevrolet"))
                .andExpect(jsonPath("$.details.model").value("Impala"))
                .andExpect(jsonPath("$.condition").value(Condition.USED.toString()))
                .andExpect(jsonPath("$.location.lat").value(40.730610))
                .andExpect(jsonPath("$.location.lon").value(-73.935242));

    }

    /**
     * Tests the deletion of a single car by ID.
     *
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    void deleteCar()
            throws Exception {
        Car car = getCar();

        mvc.perform(post(new URI("/cars")).content(json.write(car)
                                .getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(delete(new URI("/cars/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Creates an example Car object for use in testing.
     *
     * @return an example Car object
     */
    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}
