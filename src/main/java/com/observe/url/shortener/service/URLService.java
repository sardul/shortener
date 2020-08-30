package com.observe.url.shortener.service;

import com.observe.url.shortener.utils.IDGenerator;
import com.observe.url.shortener.pojo.ShortenRequest;
import com.observe.url.shortener.repository.URLRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class URLService {

    @Autowired
    private URLRepository urlRepository;

    @Value("${short_base_url}")
    private String baseUrl;

    public String shortenURL(ShortenRequest request) {
        String existingUrl  = urlRepository.getShortUrl(request);
        if (existingUrl != null){
            log.info("short url id: {} already present for {}",existingUrl, request.toString());
            return baseUrl + existingUrl;
        }
        Long id = urlRepository.incrementID();
        String uniqueID = IDGenerator.INSTANCE.createUniqueID(id);
        urlRepository.saveUrl(uniqueID, request);
        return baseUrl + uniqueID;
    }

    public String getLongURLFromID(String uniqueID) throws Exception {
        String longUrl = urlRepository.getUrl(uniqueID);
        log.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    public long getCount(String url){
        return urlRepository.getURLHitCount(url);
    }

}
