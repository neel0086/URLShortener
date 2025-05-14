package com.service.URLRedirection.service;

import com.netflix.discovery.converters.Auto;
import com.service.URLRedirection.Repository.UrlRedirectionRepository;
import com.service.URLRedirection.model.UrlMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service 
@RequiredArgsConstructor
@Slf4j
public class UrlRedirectionService {

    @Autowired
    private final UrlRedirectionRepository urlRedirectionRepository;

    @Autowired
    private final RedisTemplate<String, String> redisTemplate;

    public String getOriginalUrl(String shortKey){

        String cachedUrl = redisTemplate.opsForValue().get(shortKey);

        if(cachedUrl != null){
             return cachedUrl;

        }

        UrlMapping mapping = urlRedirectionRepository.findByShortKey(shortKey);
        if (mapping!=null){
            redisTemplate.opsForValue().set(shortKey, mapping.getOriginalUrl());
        }

        return null;
    }
}
