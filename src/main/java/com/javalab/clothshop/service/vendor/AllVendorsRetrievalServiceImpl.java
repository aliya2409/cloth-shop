package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AllVendorsRetrievalServiceImpl implements AllVendorsRetrievalService {

    private final VendorRepository vendorRepository;

    @Override
    public List<Vendor> retrieveAll() {
        return StreamSupport.stream(vendorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
