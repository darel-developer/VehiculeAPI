package com.propelize.vehicleapi;

import com.propelize.vehicleapi.config.SslConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VehicleapiApplication {

    public static void main(String[] args) throws Exception {
        SslConfig.configureSsl(); // Ajout de la configuration SSL
        SpringApplication.run(VehicleapiApplication.class, args);
    }
}