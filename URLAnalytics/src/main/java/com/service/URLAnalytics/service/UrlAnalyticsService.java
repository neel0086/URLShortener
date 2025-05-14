package com.service.URLAnalytics.service;

import com.service.URLAnalytics.model.UrlAnalytics;
import com.service.URLAnalytics.repository.UrlAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlAnalyticsService {
    @Autowired
    private final UrlAnalyticsRepository urlAnalyticsRepository;

    public String insertUrlAnalytics(String shortKey){
        return "success";
    }
}
