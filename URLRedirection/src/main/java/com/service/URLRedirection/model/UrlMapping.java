package com.service.URLRedirection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlMapping {
    @Id
    private String id;
    private String originalUrl;
    private String shortKey;
}
