package com.javalab.clothshop.vendor;

import com.javalab.clothshop.model.Vendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class TestVendorEndpoints {
    private static final String VENDOR_URL = "http://localhost:8080/api/vendors";
    private static final int TEST_VENDOR_ID = 2;

    @Value("${test.jsessionid}")
    private String jsessionid;

    @Test
    void when_getAll_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                VENDOR_URL,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getById_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                VENDOR_URL + "/" + TEST_VENDOR_ID,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                Vendor.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getProducts_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                VENDOR_URL + "/" + TEST_VENDOR_ID + "/products",
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_create_then_Ok() {
        Vendor newVendor = new Vendor();
        newVendor.setName("Kinder");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity<>(newVendor, getHeadersWithAuthentication());
        ResponseEntity response = restTemplate.exchange(
                VENDOR_URL,
                HttpMethod.POST,
                request,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_update_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        Vendor retrieved = restTemplate.exchange(
                VENDOR_URL + "/" + TEST_VENDOR_ID,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                Vendor.class).getBody();
        retrieved.setName("Gadgets");
        HttpEntity request = new HttpEntity<>(retrieved, getHeadersWithAuthentication());
        ResponseEntity response = restTemplate.exchange(
                VENDOR_URL + "/" + TEST_VENDOR_ID,
                HttpMethod.PUT,
                request,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_delete_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                VENDOR_URL + "/" + TEST_VENDOR_ID,
                HttpMethod.DELETE,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    private HttpHeaders getHeadersWithAuthentication() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "JSESSIONID=" + jsessionid);
        return requestHeaders;
    }
}
