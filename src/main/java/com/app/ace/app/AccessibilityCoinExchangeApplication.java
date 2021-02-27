package com.app.ace.app;

import com.github.lalyos.jfiglet.FigletFont;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.app.ace"})
public class AccessibilityCoinExchangeApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AccessibilityCoinExchangeApplication.class, args);
		log.info(FigletFont.convertOneLine("Service Accessibility Currency"));

	}

}