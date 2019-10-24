package com.javalab.clothshop.service.product.photo;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@Service
public class ProductPhotoServiceImpl implements ProductPhotoService {

    private static final String PRODUCT_PHOTO_PATH_JPG = "src/main/resources/static/images/product_%s.jpg";

    @Override
    public byte[] retrieveByProductId(Long productId) throws IOException {
        String path = String.format(PRODUCT_PHOTO_PATH_JPG, productId);
        File img = new File(path);
        return Files.readAllBytes(img.toPath());
    }

    @Override
    public void upload(Long productId, MultipartFile photo) throws IOException {
        String path = String.format(PRODUCT_PHOTO_PATH_JPG, productId);
        File productPhoto = new File(path);
        productPhoto.createNewFile();
        try (InputStream in = photo.getInputStream();
             OutputStream out = new FileOutputStream(productPhoto)) {
            FileCopyUtils.copy(in, out);
        }
    }
}
