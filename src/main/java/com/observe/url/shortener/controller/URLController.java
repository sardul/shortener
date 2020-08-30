package com.observe.url.shortener.controller;

import com.observe.url.shortener.pojo.ShortenRequest;
import com.observe.url.shortener.service.URLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@Slf4j
    public class URLController {
    @Autowired
    private URLService urlService;

    @RequestMapping(value = "/shortener", method= RequestMethod.POST, consumes = {"application/json"})
    public String shortenUrl(@RequestBody final ShortenRequest shortenRequest) {
        log.info("url received : " + shortenRequest.getUrl());
        String shortenedUrl = urlService.shortenURL(shortenRequest);
        log.info("Shortened url to: " + shortenedUrl);
        return shortenedUrl;
    }

    @GetMapping(value = "/{id}")
    public RedirectView redirectUrl(@PathVariable String id) throws Exception {
        log.info("Received shortened url for: " + id);
        String originalUrl = urlService.getLongURLFromID(id);
        log.info("Original URL: " + originalUrl);
        RedirectView view = new RedirectView();
        view.setUrl("http://" + originalUrl);
        return view;
    }

    @PostMapping(value = "/hitcount")
    public long getHitCount(@RequestBody ShortenRequest request) {
       return urlService.getCount(request.getUrl());
    }
}


