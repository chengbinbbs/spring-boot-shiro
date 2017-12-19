package com.chengbinbbs.springbootshiro;

import com.chengbinbbs.springbootshiro.endpoint.MyEndPoint;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
public class SpringBootShiroApplication {

	@Bean
	public MyEndPoint myEndPoint() {
		return new MyEndPoint();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootShiroApplication.class, args);
	}
}
