package com.service.URLAnalytics.service;

import com.service.URLAnalytics.model.UrlAnalytics;
import com.service.URLAnalytics.repository.UrlAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final UrlAnalyticsRepository urlAnalyticsRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String VIEWS_PREFIX = "views:";

    /**
     * Consumes Kafka messages and increments Redis counter
     */
    @KafkaListener(topics = "${kafka.analytics.topic}", groupId = "analytics-consumer")
    public void consume(UrlAnalytics urlAnalyticsDto) {
        String shortKey = urlAnalyticsDto.getShortKey();
        if (shortKey == null || shortKey.isEmpty()) {
            log.warn("Received empty shortKey in analytics message.");
            return;
        }
        // Atomic increment in Redis
        redisTemplate.opsForValue().increment(VIEWS_PREFIX + shortKey, 1);
        log.debug("Incremented views for shortKey={}", shortKey);
    }

    /**
     * Periodically flushes Redis counters into DB
     */
    @Scheduled(fixedRate = 5000)
    public void flushBatch() {
        Map<String, Long> aggregatedCounts = fetchAllViewCounts();

        if (aggregatedCounts.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Long> entry : aggregatedCounts.entrySet()) {
            String shortKey = entry.getKey();
            Long increment = entry.getValue();

            if (increment == null || increment <= 0) continue;

            UrlAnalytics analytics = urlAnalyticsRepository.findByShortKey(shortKey);

            if (analytics == null) {
                analytics = new UrlAnalytics();
                analytics.setShortKey(shortKey);
                analytics.setViewCount(increment);
            } else {
                analytics.setViewCount(analytics.getViewCount() + increment);
            }

            urlAnalyticsRepository.save(analytics);
            redisTemplate.delete(VIEWS_PREFIX + shortKey);

            log.info("Flushed analytics shortKey={}, +{} views", shortKey, increment);
        }
    }

    /**
     * Scans Redis efficiently (instead of KEYS which blocks Redis)
     */
    private Map<String, Long> fetchAllViewCounts() {
        Map<String, Long> counts = new HashMap<>();
        try (Cursor<byte[]> cursor = redisTemplate.getConnectionFactory()
                .getConnection()
                .scan(ScanOptions.scanOptions().match(VIEWS_PREFIX + "*").count(100).build())) {

            while (cursor.hasNext()) {
                String key = new String(cursor.next());
                String shortKey = key.substring(VIEWS_PREFIX.length());

                String value = redisTemplate.opsForValue().get(key);
                if (value == null) continue;

                try {
                    counts.put(shortKey, Long.parseLong(value));
                } catch (NumberFormatException e) {
                    log.error("Invalid view count value for key={}, value={}", key, value, e);
                }
            }
        } catch (Exception e) {
            log.error("Error while scanning Redis keys", e);
        }
        return counts;
    }
}
