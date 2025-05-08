package com.service.URLRedirection.Repository;

import com.service.URLRedirection.model.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRedirectionRepository extends MongoRepository<UrlMapping, String> {
    UrlMapping findByShortKey(String shortUrl);
}
