package com.udacity.boogle.maps.repository;


import com.udacity.boogle.maps.domain.*;
import org.springframework.data.jpa.repository.*;


public interface AddressRepository extends JpaRepository<Address, Long> {
}
