package com.javalab.clothshop.service.product.photo;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductPhotoService {

    byte[] retrieveByProductId(Long productId) throws IOException;

    void upload(Long productId, MultipartFile photo) throws IOException;
}
