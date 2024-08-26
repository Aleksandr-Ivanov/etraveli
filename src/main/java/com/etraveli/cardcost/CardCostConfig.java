package com.etraveli.cardcost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EntityScan(basePackages = {"com.etraveli.cardcost.entity"})
@EnableTransactionManagement
@EnableJpaRepositories("com.etraveli.cardcost.repo")
public class CardCostConfig {

  @Bean
  RestTemplate restTemplate(@Autowired RestTemplateBuilder builder) {
    return builder.build();
  }
}
