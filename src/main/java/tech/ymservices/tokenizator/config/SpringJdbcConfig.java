package tech.ymservices.tokenizator.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("tech.ymservices.tokenizator")
public class SpringJdbcConfig {

    @Bean
    DataSource irisDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.intersystems.jdbc.IRISDriver");
        dataSource.setUrl("jdbc:IRIS://k8s-c77a362a-a86edf71-103d6ad296-7a48f474e73c4c5d.elb.us-east-1.amazonaws.com:1972/User");
        dataSource.setUsername("SQLAdmin");
        dataSource.setPassword("Iris@2023");

        return dataSource;
    }

}
