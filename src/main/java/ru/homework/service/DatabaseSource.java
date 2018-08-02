package ru.homework.service;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import ru.homework.configuration.DatabaseSettings;

@Component
public class DatabaseSource {
	
	private final DatabaseSettings databaseSettings;

	public DatabaseSource(DatabaseSettings databaseSettings) {
		this.databaseSettings = databaseSettings;
	}
	
	@Bean
	public DataSource dataSource() {
		DataSourceBuilder<?> DS = DataSourceBuilder.create();
		DS.driverClassName("org.postgresql.Driver")
			.url("jdbc:postgresql://" + databaseSettings.getHost() + ":" + 
										databaseSettings.getPort() + "/" + 
										databaseSettings.getBase())
			.username(databaseSettings.getUser())
			.password(databaseSettings.getPassword());
		return DS.build();		
	}
	
}
