package com.service.URLAnalytics.service;

import com.service.URLAnalytics.model.UrlAnalytics;
import com.service.URLAnalytics.repository.UrlAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final UrlAnalyticsRepository urlAnalyticsRepository;

    // In-memory buffer to collect shortKey view increments
    private final Map<String, Long> viewCountBuffer = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    private final RedisTemplate<String, String> redisTemplate;
    @KafkaListener(topics = "${kafka.analytics.topic}", groupId = "analytics-consumer")
    public void consume(UrlAnalytics urlAnalyticsDto) {
        String shortKey = urlAnalyticsDto.getShortKey();
        synchronized (viewCountBuffer) {
//            viewCountBuffer.put(shortKey, viewCountBuffer.getOrDefault(shortKey, 0L) + 1);
            redisTemplate.opsForValue().increment("views:"+shortKey, 1);
        }
    }

    @Scheduled(fixedRate = 5000)
    public void flushBatch(){
        Set<String> keys = redisTemplate.keys("views:*");
        if(keys == null || keys.isEmpty()) return;
        for(String key : keys){
            String shortKey = key.substring("views:".length());
            Long increment = Long.valueOf(redisTemplate.opsForValue().get(key));

            if(increment == null) continue;
            UrlAnalytics urlAnalytics = urlAnalyticsRepository.findByShortKey(shortKey);
            if(urlAnalytics==null){
                urlAnalytics = new UrlAnalytics();
                urlAnalytics.setShortKey(shortKey);
                urlAnalytics.setViewCount(increment);
            }
            else{
                urlAnalytics.setViewCount(urlAnalytics.getViewCount()+increment);
            }
            urlAnalyticsRepository.save(urlAnalytics);
            redisTemplate.delete(key);
            log.info("Flushed info {}: +{}", shortKey, increment);
        }
    }

}