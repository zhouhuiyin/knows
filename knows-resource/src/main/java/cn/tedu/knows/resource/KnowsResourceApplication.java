package cn.tedu.knows.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// 下面的注解标记之后,项目启动时会注册到注册中心,成为一个微服务项目
@EnableDiscoveryClient   //EDC
public class KnowsResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowsResourceApplication.class, args);
	}

}
