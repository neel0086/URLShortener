package com.service.URLAnalytics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "urlAnalytics") // Collection name explicitly set
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlAnalytics {

    @Id
    private String id;

    private String shortKey;
    private long viewCount;
}
