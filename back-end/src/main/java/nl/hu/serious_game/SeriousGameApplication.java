package nl.hu.serious_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude =
		{DataSourceAutoConfiguration.class}
)
public class SeriousGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeriousGameApplication.class, args);
	}

}
