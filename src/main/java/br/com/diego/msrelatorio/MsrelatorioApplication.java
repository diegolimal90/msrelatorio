package br.com.diego.msrelatorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "br.com.diego.msrelatorio")
public class MsrelatorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsrelatorioApplication.class, args);
    }

}
