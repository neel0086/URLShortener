package com.service.URLAnalytics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlAnalytics {
    @Id
    private String id;
    private String shortKey;
    private int viewCount;
}
