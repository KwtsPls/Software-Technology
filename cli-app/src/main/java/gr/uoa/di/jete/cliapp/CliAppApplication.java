package gr.uoa.di.jete.cliapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CliAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CliAppApplication.class, args);
	}

}
