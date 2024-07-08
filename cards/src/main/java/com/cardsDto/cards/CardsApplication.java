package com.cardsDto.cards;

import com.cardsDto.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value={CardsContactInfoDto.class})
@OpenAPIDefinition(
		info=@Info(title = "Cards microservice Rest Api Documentation",
				description = "Bank Accounts microservice Rest Api Documentation",
				version = "v1",
				contact = @Contact(
						name="Jordi D",
						email = "devjordi.jd97@gmail.com"
				),
				license = @License(
						name="Apache 2.0"
				))
)public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
