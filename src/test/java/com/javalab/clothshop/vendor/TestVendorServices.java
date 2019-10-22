package com.javalab.clothshop.vendor;

import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.repository.VendorRepository;
import com.javalab.clothshop.repository.exception.NameTakenException;
import com.javalab.clothshop.service.vendor.VendorRemovalServiceImpl;
import com.javalab.clothshop.service.vendor.VendorRetrievalServiceImpl;
import com.javalab.clothshop.service.vendor.VendorSavingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestVendorServices {

    @Mock
    VendorRepository vendorRepository;
    @InjectMocks
    VendorSavingServiceImpl vendorSavingService;
    @InjectMocks
    VendorRemovalServiceImpl vendorRemovalService;
    @InjectMocks
    VendorRetrievalServiceImpl vendorRetrievalService;
    private Vendor vendor;
    private Product product;

    @BeforeEach
    public void setup() {
        vendor = new Vendor();
        vendor.setName("White swan");
        vendor.setId(1L);
        product = Product.builder().vendor(vendor).name("Cup").quantity(10).price(100).build();
        List<Product> products = new ArrayList<>();
        products.add(product);
        vendor.setProducts(products);
    }

    @Test
    public void test_throwsNameTaken() {
        when(vendorRepository.findByName(anyString())).thenReturn(Optional.of(vendor));
        Vendor vendorToSave = new Vendor();
        vendorToSave.setName(vendor.getName());
        assertThrows(NameTakenException.class, () -> vendorSavingService.save(vendorToSave));
    }

    @Test
    public void test_save() {
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        Vendor vendorToSave = new Vendor();
        vendorToSave.setName(vendor.getName());
        Vendor saved = vendorSavingService.save(vendorToSave);
        assertEquals(vendor.getId(), saved.getId());
    }

    @Test
    public void test_getById() {
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        Vendor retrieved = vendorRetrievalService.retrieveById(1L);
        assertEquals(vendor.getName(), retrieved.getName());
    }

    @Test
    public void test_getAll() {
        List<Vendor> categories = new ArrayList<>();
        categories.add(vendor);
        when(vendorRepository.findAll()).thenReturn(categories);
        List<Vendor> retrieved = vendorRetrievalService.retrieveAll();
        assertEquals(categories.size(), retrieved.size());
        assertTrue(retrieved.contains(vendor));
    }

    @Test
    public void test_delete() {
        vendorRemovalService.removeById(2L);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}
