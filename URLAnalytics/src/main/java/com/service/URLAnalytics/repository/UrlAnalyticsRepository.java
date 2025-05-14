package com.service.URLAnalytics.repository;

import com.service.URLAnalytics.model.UrlAnalytics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlAnalyticsRepository extends MongoRepository<UrlAnalytics, String> {
}
