package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.repository.VendorRepository;
import com.javalab.clothshop.repository.exception.NameTakenException;
import com.javalab.clothshop.repository.exception.VendorNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendorSavingServiceImpl implements VendorSavingService {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor save(Long id, Vendor vendor) {
        if (vendorRepository.existsById(id)) {
            return vendorRepository.save(vendor);
        } else {
            throw new VendorNotFoundException("Could not find vendor with id: " + id);
        }
    }

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
