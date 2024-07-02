package project.everyarchive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EveryarchiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveryarchiveApplication.class, args);
	}

}
