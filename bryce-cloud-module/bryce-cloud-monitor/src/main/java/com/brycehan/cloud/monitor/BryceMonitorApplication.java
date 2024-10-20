package com.brycehan.cloud.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Bryce监控应用
 *
 * @since 2023/11/18
 * @author Bryce Han
 */
@EnableAdminServer
@SpringBootApplication
public class BryceMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceMonitorApplication.class, args);
    }

}
