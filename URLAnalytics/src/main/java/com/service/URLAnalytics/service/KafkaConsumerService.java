package com.service.URLAnalytics.service;

import com.service.URLAnalytics.model.UrlAnalytics;
import com.service.URLAnalytics.repository.UrlAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final UrlAnalyticsRepository urlAnalyticsRepository;

    // In-memory buffer to collect shortKey view increments
    private final Map<String, Long> viewCountBuffer = Collections.synchronizedMap(new HashMap<>());

    @KafkaListener(topics = "${kafka.analytics.topic}", groupId = "analytics-consumer")
    public void consume(UrlAnalytics urlAnalyticsDto) {
        System.out.println(viewCountBuffer);
        String shortKey = urlAnalyticsDto.getShortKey();
        synchronized (viewCountBuffer) {
            viewCountBuffer.put(shortKey, viewCountBuffer.getOrDefault(shortKey, 0L) + 1);
        }
    }

    @Scheduled(fixedRate = 50000)
    public void flushBatch() {
        Map<String, Long> batch;
        synchronized (viewCountBuffer) {
            if (viewCountBuffer.isEmpty()) return;
            batch = new HashMap<>(viewCountBuffer);
            viewCountBuffer.clear();
        }

        for (Map.Entry<String, Long> entry : batch.entrySet()) {
            String shortKey = entry.getKey();
            long increment = entry.getValue();

            UrlAnalytics analytics = urlAnalyticsRepository.findByShortKey(shortKey);
            if (analytics == null) {
                analytics = new UrlAnalytics();
                analytics.setShortKey(shortKey);
                analytics.setViewCount(increment);
            } else {
                analytics.setViewCount(analytics.getViewCount() + increment);
            }

            urlAnalyticsRepository.save(analytics);
            log.info("Flushed view count for {}: +{}", shortKey, increment);
        }
    }
}
