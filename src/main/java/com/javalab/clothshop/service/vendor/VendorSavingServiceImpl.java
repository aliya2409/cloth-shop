package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.repository.VendorRepository;
import com.javalab.clothshop.repository.exception.NameTakenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendorSavingServiceImpl implements VendorSavingService {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor save(Vendor vendor) {
        return vendorRepository.save(checkNameUniqueness(vendor));
    }

    private Vendor checkNameUniqueness(Vendor vendor) {
        if (!vendorRepository.findByName(vendor.getName()).isPresent()) {
            return vendor;
        } else {
            throw new NameTakenException("Provided name is already taken: " + vendor.getName());
        }
    }
}
