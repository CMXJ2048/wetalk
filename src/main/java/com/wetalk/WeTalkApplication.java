package com.wetalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point for WeTalk.
 * <p>
 * - @SpringBootApplication enables component scanning, auto-configuration,
 *   and configuration properties within the base package {@code com.wetalk}.
 * - Run the {@code main} method to start the embedded server (Tomcat) on port 8080 by default.
 */
@SpringBootApplication
public class WeTalkApplication {

    /**
     * Bootstraps the Spring application context and starts the embedded web server.
     */
    public static void main(String[] args) {
        SpringApplication.run(WeTalkApplication.class, args);
    }
}