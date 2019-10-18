package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendorSavingServiceImpl implements VendorSavingService {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor save(Vendor vendor) {
        return vendorRepository.save(vendor);
    }
}
