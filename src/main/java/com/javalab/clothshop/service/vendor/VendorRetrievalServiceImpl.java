package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.repository.VendorRepository;
import com.javalab.clothshop.repository.exception.VendorNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class VendorRetrievalServiceImpl implements VendorRetrievalService {

    private final VendorRepository vendorRepository;

    @Override
    @Transactional
    public Vendor retrieveById(Long id) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(() -> new VendorNotFoundException("Could not find vendor with id: " + id));
        Hibernate.initialize(vendor.getProducts());
        return vendor;
    }

    @Override
    public List<Vendor> retrieveAll() {
        return StreamSupport.stream(vendorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
