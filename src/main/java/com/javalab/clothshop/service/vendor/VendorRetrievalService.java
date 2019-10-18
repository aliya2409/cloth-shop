package com.javalab.clothshop.service.vendor;

import com.javalab.clothshop.model.Vendor;

import java.util.List;

public interface VendorRetrievalService {

    Vendor retrieveById(Long id);

    List<Vendor> retrieveAll();
}
