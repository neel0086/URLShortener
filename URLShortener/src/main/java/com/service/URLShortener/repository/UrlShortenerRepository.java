package com.service.URLShortener.repository;

import com.service.URLShortener.model.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlShortenerRepository extends MongoRepository<UrlMapping, String> {
}
