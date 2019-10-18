package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendorRemovalServiceImpl implements VendorRemovalService {

    private final VendorRepository vendorRepository;

    @Override
    public void removeById(Long id) {
        vendorRepository.deleteById(id);
    }
}
