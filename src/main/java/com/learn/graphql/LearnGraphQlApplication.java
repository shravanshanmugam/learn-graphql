package com.learn.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Map;

@EnableReactiveMethodSecurity
@SpringBootApplication
public class LearnGraphQlApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnGraphQlApplication.class, args);
	}

	@Bean
	MapReactiveUserDetailsService authentication() {
		var users = Map.of(
						"shravan", new String[]{"USER"},
						"shanmugam", new String[]{"ADMIN", "USER"})
				.entrySet()
				.stream()
				.map(e -> User.withDefaultPasswordEncoder()
						.username(e.getKey())
						.password("pwd")
						.roles(e.getValue())
						.build())
				.toList();
		return new MapReactiveUserDetailsService(users);
	}

	@Bean
	SecurityWebFilterChain authorization(ServerHttpSecurity http) {
		return http
				.csrf(x -> x.disable())
				.authorizeExchange(ae -> ae.anyExchange().permitAll())
				.httpBasic(Customizer.withDefaults())
				.build();
	}
}
