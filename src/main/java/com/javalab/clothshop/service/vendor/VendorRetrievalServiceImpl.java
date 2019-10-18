package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.repository.VendorRepository;
import com.javalab.clothshop.repository.exception.VendorNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendorRetrievalServiceImpl implements VendorRetrievalService {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor retrieveById(Long id) {
        return vendorRepository.findById(id).orElseThrow(() -> new VendorNotFoundException("Could not find vendor with id: " + id));
    }
}
