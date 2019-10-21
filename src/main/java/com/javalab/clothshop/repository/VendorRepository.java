package com.javalab.clothshop.repository;

import com.javalab.clothshop.model.Vendor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VendorRepository extends CrudRepository<Vendor, Long> {

    Optional<Vendor> findByName(String name);
}
