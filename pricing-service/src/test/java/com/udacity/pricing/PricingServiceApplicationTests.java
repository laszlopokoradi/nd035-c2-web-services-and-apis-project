package com.udacity.pricing;


import com.udacity.pricing.domain.price.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.rest.webmvc.support.*;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PricingServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositoryEntityLinks entityLinks;

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateCorrectPrice()
            throws Exception {

        mockMvc.perform(post(entityLinks.linkFor(Price.class)
                                        .toUri())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"vehicleId\": 1, \"currency\": \"USD\", \"amount\": 10000.0}"))
               .andExpect(status().isCreated())
               .andExpect(content().contentType("application/hal+json"));
    }

    @Test
    void testGetCreatedPrice()
            throws Exception {
        mockMvc.perform(post(entityLinks.linkFor(Price.class)
                                        .toUri())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"vehicleId\": 2, \"currency\": \"USD\", \"amount\": 10000.0}"))
               .andExpect(status().isCreated());

        mockMvc.perform(get(entityLinks.linkToSearchResource(Price.class, LinkRelation.of("findByVehicleId"))
                                       .toUri())
                       .param("vehicleId", "2"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/hal+json"))
               .andExpect(jsonPath("$.vehicleId").value(2))
               .andExpect(jsonPath("$.currency").value("USD"))
               .andExpect(jsonPath("$.amount").value(10000.0));
    }

    @Test
    void testGetPrices()
            throws Exception {
        mockMvc.perform(post(entityLinks.linkFor(Price.class)
                                        .toUri())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"vehicleId\": 3, \"currency\": \"USD\", \"amount\": 30000.0}"))
               .andExpect(status().isCreated());

        mockMvc.perform(post(entityLinks.linkFor(Price.class)
                                        .toUri())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"vehicleId\": 4, \"currency\": \"USD\", \"amount\": 40000.0}"))
               .andExpect(status().isCreated());

        mockMvc.perform(get(entityLinks.linkToCollectionResource(Price.class)
                                       .toUri()))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.page.totalElements").isNotEmpty());
    }

}
