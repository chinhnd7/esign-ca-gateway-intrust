package vn.intrustca.esigncagateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EsignCaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EsignCaGatewayApplication.class);
		application.run(args);
	}
}
