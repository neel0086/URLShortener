package com.service.URLShortener;

import com.service.URLShortener.service.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertEquals;

class URLShortenerServiceTest {

	private UrlShortenerService urlShortenerService;

	@BeforeEach
	void setUp() {
		// Initialize the service
		urlShortenerService = new UrlShortenerService(null);  // Using null as the argument for simplicity, replace with actual mock if needed
	}

	@Test
	void testEncodeBase62() throws Exception {
		Method method = UrlShortenerService.class.getDeclaredMethod("encodeBase62", long.class);
		method.setAccessible(true);

		long input = 1234567;
		String encoded = (String) method.invoke(urlShortenerService, input);

		System.out.println("Encoded Base62: " + encoded);

		assertEquals("5ban", encoded, "The Base62 encoding is incorrect");
	}

}
