package com.najo.tutorial.app;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.najo.tutorial.data.Account;
import com.najo.tutorial.data.AccountRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"com.najo"})
@EnableJpaRepositories("com.najo.tutorial")
@EntityScan("com.najo.tutorial")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AccountRepository accountRepository) {
		return (evt) -> Arrays.asList("jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
				.forEach(a -> {
					Account account = accountRepository.save(new Account(a, "password"));
				});
	}

}
