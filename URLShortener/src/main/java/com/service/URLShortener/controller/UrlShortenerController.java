package com.service.URLShortener.controller;

import com.service.URLShortener.dto.UrlRequest;
import com.service.URLShortener.dto.UrlResponse;
import com.service.URLShortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/urlshortener")
public class UrlShortenerController {

    @Autowired
    private final UrlShortenerService urlShortenerService;


    @GetMapping("/")
    public ResponseEntity<String> entryCheck(){
        return ResponseEntity.ok("Inside the URL Shortener");
    }
    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest urlRequest){
        return ResponseEntity.ok(urlShortenerService.shortenUrl(urlRequest));
    }
}
