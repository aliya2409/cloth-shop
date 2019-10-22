package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.model.Vendor;

public interface VendorSavingService {

    Vendor save(Vendor vendor);

    Vendor save(Long id, Vendor product);
}
