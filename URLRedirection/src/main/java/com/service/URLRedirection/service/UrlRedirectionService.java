package com.service.URLRedirection.service;

import com.service.URLRedirection.Repository.UrlRedirectionRepository;
import com.service.URLRedirection.model.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlRedirectionService {

    @Autowired
    private final UrlRedirectionRepository urlRedirectionRepository;

    public String getOriginalUrl(String shortKey){
        UrlMapping mapping = urlRedirectionRepository.findByShortKey(shortKey);

        return mapping!=null ? mapping.getOriginalUrl() : null;
    }
}
