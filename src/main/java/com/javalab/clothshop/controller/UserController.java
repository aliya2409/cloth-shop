package com.javalab.clothshop.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalab.clothshop.model.User;
import com.javalab.clothshop.service.user.UserRemovalService;
import com.javalab.clothshop.service.user.UserRetrievalService;
import com.javalab.clothshop.service.user.UserSavingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserRetrievalService userRetrievalService;
    private final UserRemovalService userRemovalService;
    private final UserSavingService userSavingService;

//    @Value("${security.oauth2.client.access-token-uri}")
//    private String accessTokenUri;
//
//    @Value("${security.oauth2.resource.user-info-uri}")
//    private String userInfoUri;
//
//    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
//    private String clientSecret;
//
//    @Value("${spring.security.oauth2.client.registration.github.client-id}")
//    private String clientId;

    @GetMapping
    public List<User> getAll() {
        return userRetrievalService.retrieveAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userSavingService.save(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userRetrievalService.retrieveBy(id);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id) {
        return userSavingService.save(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        userRemovalService.removeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }

    @GetMapping("/principal")
    public Principal login(Principal principal) {
        return principal;
    }
//    public ResponseEntity login(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException {
//        ResponseEntity<String> response = null;
//        System.out.println("Authorization Code------" + code);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//        HttpEntity<String> request = new HttpEntity<String>(headers);
//
//        String access_token_url = accessTokenUri;
//        access_token_url += "?code=" + code;
//        access_token_url += "&client_id=" + clientId;
//        access_token_url += "&client_secret=" + clientSecret;
//        access_token_url += "&state=" + state;
//
//        response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = mapper.readTree(response.getBody());
//        String token = node.path("access_token").asText();
//
//        System.out.println("Access Token Response ---------" + response.getBody());
//
//        HttpHeaders headersForUserInfo = new HttpHeaders();
//        headersForUserInfo.add("Authorization", "Bearer " + token);
//        HttpEntity<String> requestForUserInfo = new HttpEntity<String>(headersForUserInfo);
//        response = restTemplate.exchange(userInfoUri, HttpMethod.GET, requestForUserInfo, String.class);
//        System.out.println("User Info Response ---------" + response.getBody());
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return new ResponseEntity(null, responseHeaders, HttpStatus.OK);
//    }
//
//    @GetMapping("/logout")
//    public ResponseEntity logout() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return new ResponseEntity(null, headers, HttpStatus.OK);
//    }
}
