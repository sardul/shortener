package com.observe.url.shortener.repository;

import com.observe.url.shortener.pojo.ShortenRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Slf4j
public class URLRepository {
    private AtomicLong counter = new AtomicLong(1);
    private Map<ShortenRequest, String> reqIdMap;
    private Map<String, String> idUrlMap;
    private Map<String, Long> urlHitCountMap;


    public URLRepository() {
        reqIdMap = new HashMap<>();
        idUrlMap = new HashMap<>();
        urlHitCountMap = new HashMap<>();
    }

    public Long incrementID() {
        long id = counter.getAndIncrement();
        log.info("Incrementing ID: {}", id);
        return id;
    }

    public void saveUrl(String uniqueId,ShortenRequest request) {
        log.info("Saving url: {} cid: {} at {}", request.getUrl(),request.getCid(), uniqueId);
        reqIdMap.put(request, uniqueId);
        idUrlMap.put(uniqueId, request.getUrl());
        urlHitCountMap.put(request.getUrl(),0L);
    }


    public String getUrl(String id) throws Exception {
        log.info("Retrieving at {}", id);
        String url = idUrlMap.get(id);
        log.info("Retrieved {} at {}", url ,id);
        if (url == null) {
            throw new Exception("URL at key" + id + " does not exist");
        }
        urlHitCountMap.computeIfPresent(url,(k, v)->v+1);
        return url;
    }

    public long getURLHitCount(String url) {
        if (urlHitCountMap.containsKey(url)){
            return urlHitCountMap.get(url);
        }
        return  0L;
    }

    public String getShortUrl(ShortenRequest key) {
        return reqIdMap.get(key);
    }
}
