package fi.seamk.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@RestController
	public static class HelloController {
		@GetMapping("/")
		public String hello() {
			return "Hello. Running an auto deployed image from docker hub! Test 2";
		}

		@GetMapping("/api/hello")
		public String helloApi() {
			return "Hello from REST API!";
		}
	}

}
