package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:demo-test")
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
