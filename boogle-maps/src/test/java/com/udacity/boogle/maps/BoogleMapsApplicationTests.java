package com.udacity.boogle.maps;


import com.fasterxml.jackson.databind.*;
import com.udacity.boogle.maps.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.web.servlet.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BoogleMapsApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAddressOnce() {
        Address address = null;
        try {
            String addressJson = mockMvc.perform(get("/maps")
                                                .param("lat", "37.7749")
                                                .param("lon", "-122.4194"))
                                        .andExpect(status().isOk())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();
            address = new ObjectMapper().readValue(addressJson, Address.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(address).isNotNull();
        assertThat(address.getStreetAndNumber()).isNotNull();
        assertThat(address.getCity()).isNotNull();
        assertThat(address.getState()).isNotNull();
        assertThat(address.getZip()).isNotNull();

    }

    @Test
    void testGetAddressTwice() {
        String firstResultJson = null;
        String secondResultJson = null;
        try {
            firstResultJson = mockMvc.perform(get("/maps")
                                             .param("lat", "47.7749")
                                             .param("lon", "122.4194"))
                                     .andExpect(status().isOk())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

            secondResultJson = mockMvc.perform(get("/maps")
                                              .param("lat", "47.7749")
                                              .param("lon", "122.4194"))
                                      .andExpect(status().isOk())
                                      .andReturn()
                                      .getResponse()
                                      .getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(firstResultJson).isNotNull();
        assertThat(secondResultJson).isNotNull();
        assertThat(firstResultJson).isEqualTo(secondResultJson);

    }

}
