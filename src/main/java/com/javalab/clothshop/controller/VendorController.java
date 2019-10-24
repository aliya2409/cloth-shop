package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.service.vendor.VendorRemovalService;
import com.javalab.clothshop.service.vendor.VendorRetrievalService;
import com.javalab.clothshop.service.vendor.VendorSavingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vendors")
@AllArgsConstructor
public class VendorController {

    private final VendorRetrievalService vendorRetrievalService;
    private final VendorRemovalService vendorRemovalService;
    private final VendorSavingService vendorSavingService;

    @GetMapping
    public List<Vendor> getAll() {
        return vendorRetrievalService.retrieveAll();
    }

    @PostMapping
    public Vendor create(@RequestBody Vendor vendor) {
        return vendorSavingService.save(vendor);
    }

    @GetMapping("/{id}")
    public Vendor getById(@PathVariable Long id) {
        return vendorRetrievalService.retrieveById(id);
    }

    @PutMapping("/{id}")
    public Vendor update(@RequestBody Vendor vendor, @PathVariable Long id) {
        return vendorSavingService.save(id, vendor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        vendorRemovalService.removeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }
}
