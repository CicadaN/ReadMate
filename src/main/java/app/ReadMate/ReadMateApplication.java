package app.ReadMate;

import app.ReadMate.model.User;
import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class ReadMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadMateApplication.class, args);
	}

	@Bean
	public Faker faker() {
		return new Faker();
	}
	//todo Создать список из 10 пользователей, 10 книг

}
