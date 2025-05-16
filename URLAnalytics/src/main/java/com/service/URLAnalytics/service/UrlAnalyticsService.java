package com.service.URLAnalytics.service;

import com.service.URLAnalytics.model.UrlAnalytics;
import com.service.URLAnalytics.repository.UrlAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlAnalyticsService {
    @Autowired
    private final UrlAnalyticsRepository urlAnalyticsRepository;

    public void saveAll(List<UrlAnalytics> data){
        urlAnalyticsRepository.saveAll(data);
        log.info("Batch size {} analytics records", data.size());
    }

    public void incrementView(String shortKey) {
        UrlAnalytics analytics = urlAnalyticsRepository.findById(shortKey).orElse(
                UrlAnalytics.builder().shortKey(shortKey).viewCount(0).build()
        );
        analytics.setViewCount(analytics.getViewCount() + 1);
        urlAnalyticsRepository.save(analytics);
    }
}
