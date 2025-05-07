package com.service.URLShortener.service;

import com.service.URLShortener.dto.UrlRequest;
import com.service.URLShortener.dto.UrlResponse;
import com.service.URLShortener.model.UrlMapping;
import com.service.URLShortener.repository.UrlShortenerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerService {

    @Autowired
    private final UrlShortenerRepository urlShortenerRepository;

    @Autowired
    private CounterService counterService;

    private final String DOMAIN = "http://short.est/";

    public UrlResponse shortenUrl(UrlRequest urlShortenerRequest){
        long counter = counterService.getNextCounter();
        String shortKey = encodeBase62(counter);
        String shortUrl = DOMAIN+shortKey;

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(urlShortenerRequest.getOriginalUrl());
        urlMapping.setShortUrl(shortUrl);
        urlShortenerRepository.save(urlMapping);

        return new UrlResponse(shortUrl);


    }
    public String getOriginalUrl(String shortKey){
        UrlMapping mapping = urlShortenerRepository.findByShortUrl(shortKey);
        return mapping!=null ? mapping.getOriginalUrl() : null;
    }

    public String encodeBase62(long num) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(chars.charAt((int)(num % 62)));
            num /= 62;
        }

        return sb.reverse().toString();
    }




}
