package com.service.URLShortener.service;

import com.service.URLShortener.model.Counter;
import com.service.URLShortener.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    private static final String COUNTER_ID="urlCounter";

    @Autowired
    private CounterRepository counterRepository;

    public synchronized long getNextCounter(){
        Counter counter = counterRepository.findById(COUNTER_ID).orElseGet(()->{
            Counter newCounter = new Counter();
            newCounter.setId(COUNTER_ID);
            newCounter.setValue(0);
            return counterRepository.save(newCounter);
        });
        counter.setValue(counter.getValue()+1);
        counterRepository.save(counter);
        return counter.getValue();
    }
}
