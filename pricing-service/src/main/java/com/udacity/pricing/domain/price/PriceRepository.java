package com.udacity.pricing.domain.price;


import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;


@Repository
public interface PriceRepository
        extends JpaRepository<Price, Long> {
    Optional<Price> findByVehicleId(Long vehicleId);
}
