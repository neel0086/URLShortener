package com.service.URLShortener.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="urlMapping")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UrlMapping {
    @Id
    private String id;
    private String originalUrl;
    private String shortKey;
}
