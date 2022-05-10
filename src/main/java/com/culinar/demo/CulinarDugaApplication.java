package com.culinar.demo;

import com.culinar.demo.model.Recept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
public class CulinarDugaApplication {

	public static void main(String[] args) throws SQLException {
		ReceptDAO result = new ReceptDAO();
		List<Recept> list = result.index();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getHead());
			String msg = java.util.Arrays.toString(list.get(i).getIngredients());
			System.out.println(msg);
		}
		SpringApplication.run(CulinarDugaApplication.class, args);

	}
}
