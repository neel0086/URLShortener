package com.service.URLRedirection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClickEvent {
    private String shortUrl;
    private String timestamp;
    private String ip;
    private String userAgent;
}
