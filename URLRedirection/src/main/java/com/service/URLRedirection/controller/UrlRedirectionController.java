package com.service.URLRedirection.controller;

import com.service.URLRedirection.service.UrlRedirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/urlredirection")
public class UrlRedirectionController {

    @Autowired
    private final UrlRedirectionService urlRedirectionService;

    @GetMapping("/{shortKey}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortKey){
        String originalUrl = urlRedirectionService.getOriginalUrl(shortKey);
        if(originalUrl!=null){
            return ResponseEntity.ok(originalUrl);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
