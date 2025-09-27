package com.example.GoodPlace;

import com.example.GoodPlace.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class GoodPlaceApplicationTests {

	@MockBean
	private EmailService emailService;

	@Test
	void contextLoads() {
	}

}