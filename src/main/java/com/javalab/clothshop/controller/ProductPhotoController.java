package com.javalab.clothshop.controller;

import com.javalab.clothshop.service.product.ProductRetrievalService;
import com.javalab.clothshop.service.product.photo.ProductPhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("products/{id}/photo")
@AllArgsConstructor
public class ProductPhotoController {

    private final ProductRetrievalService productRetrievalService;
    private final ProductPhotoService productPhotoService;

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProductPhoto(@PathVariable Long id) throws IOException {
        byte[] result = productPhotoService.retrieveByProductId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity upload(@PathVariable Long id, @RequestPart MultipartFile photo) throws IOException {
        productRetrievalService.existsById(id);
        productPhotoService.upload(id, photo);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }
}
