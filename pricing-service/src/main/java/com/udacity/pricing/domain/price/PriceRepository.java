package com.udacity.pricing.domain.price;


import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;


@Repository
public interface PriceRepository
        extends JpaRepository<Price, Long> {
    Price findByVehicleId(Long vehicleId);
}
