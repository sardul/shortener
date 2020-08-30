package com.observe.url.shortener;

import com.observe.url.shortener.controller.URLController;
import com.observe.url.shortener.pojo.ShortenRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootTest
class UrlShortenerApplicationTests {
	@Autowired
	private URLController 	urlController;

	@Test
	@Order(1)
	void contextLoads() {
	}

	@Test
	@Order(2)
	public void testUrlShort() throws Exception {
		String shortUrl = urlController.shortenUrl(new ShortenRequest("www.google.com","C1"));
		Assert.assertEquals(shortUrl,"http://localhost:8080/b");
		RedirectView view = urlController.redirectUrl("b");
		Assert.assertEquals(view.getUrl(),"http://www.google.com");
		Assert.assertEquals(1,urlController.getHitCount(new ShortenRequest("www.google.com", null)));
	}

}
