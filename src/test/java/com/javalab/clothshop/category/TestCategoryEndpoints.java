package com.javalab.clothshop.category;

import com.javalab.clothshop.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class TestCategoryEndpoints {

    private static final String CATEGORIES_URL = "http://localhost:8080/api/categories";
    private static final int TEST_CATEGORY_ID = 1;

    @Value("${test.jsessionid}")
    private String jsessionid;

    @Test
    void when_getAll_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                CATEGORIES_URL,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getById_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                CATEGORIES_URL + "/" + TEST_CATEGORY_ID,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                Category.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getProducts_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                CATEGORIES_URL + "/" + TEST_CATEGORY_ID + "/products",
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_create_then_Ok() {
        Category newCategory = new Category();
        newCategory.setName("Sweets");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity<>(newCategory, getHeadersWithAuthentication());
        ResponseEntity response = restTemplate.exchange(
                CATEGORIES_URL,
                HttpMethod.POST,
                request,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    private HttpHeaders getHeadersWithAuthentication() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "JSESSIONID=" + jsessionid);
        return requestHeaders;
    }
}
