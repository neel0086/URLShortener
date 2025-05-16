package com.service.URLRedirection.service;

import com.netflix.discovery.converters.Auto;
import com.service.URLRedirection.Repository.UrlRedirectionRepository;
import com.service.URLRedirection.dto.UrlAnalyticsDto;
import com.service.URLRedirection.model.UrlMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service 
@RequiredArgsConstructor
@Slf4j
public class UrlRedirectionService {

    @Autowired
    private final UrlRedirectionRepository urlRedirectionRepository;

    @Autowired
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    private final KafkaProducerService kafkaProducerService;

    public String getOriginalUrl(String shortKey,String ip ){

        String cachedUrl = redisTemplate.opsForValue().get(shortKey);

        if(cachedUrl != null){
            sendAnalytics(shortKey);
            return cachedUrl;

        }

        UrlMapping mapping = urlRedirectionRepository.findByShortKey(shortKey);
        if (mapping!=null){
            redisTemplate.opsForValue().set(shortKey, mapping.getOriginalUrl());
            sendAnalytics(shortKey);
            return mapping.getOriginalUrl();
        }
        return null;
    }
    private void sendAnalytics(String shortKey) {
        UrlAnalyticsDto dto = UrlAnalyticsDto.builder()
                .shortKey(shortKey)
                .build();
        kafkaProducerService.sendToKafka(dto);
    }

}
