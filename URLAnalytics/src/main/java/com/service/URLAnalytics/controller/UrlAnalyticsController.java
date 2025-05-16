package com.service.URLAnalytics.controller;

import com.service.URLAnalytics.service.UrlAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/urlanalytics")
@RequiredArgsConstructor
public class UrlAnalyticsController {

    @Autowired
    private final UrlAnalyticsService urlAnalyticsService;

    @PostMapping("/{shortKey}/urlanalytics")
    public ResponseEntity<String> insertUrlAnalytics(@PathVariable String shortKey){
        return ResponseEntity.ok("Success");
    }

}
