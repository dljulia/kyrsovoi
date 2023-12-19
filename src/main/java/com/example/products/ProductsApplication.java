package com.example.products;

import com.example.products.controllers.ProductController;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.logging.*;

@SpringBootApplication
public class ProductsApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(ProductsApplication.class, args);

		Logger logger = Logger.getLogger(ProductController.class.getName());
		Handler fileHandler = new FileHandler("/Users/dzianistupik/config/config.rtf", 100000*1024, 10, false);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.log(Level.INFO, "Start working!");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry reg) {
				reg.addMapping("/**").allowedOrigins("*");
			}
		};

	}
}
