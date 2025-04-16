package com.udacity.boogle.maps.repository;


import com.udacity.boogle.maps.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;


public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l WHERE l.latitude = :latitude AND l.longitude = :longitude")
    Optional<Location> findByCoordinates(Double latitude, Double longitude);
}
