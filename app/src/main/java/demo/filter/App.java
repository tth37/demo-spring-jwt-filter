/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package demo.filter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "bearer-token", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
