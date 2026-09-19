package socialapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class apiApplication {

    public static void main(String[] args) {
        SpringApplication.run(apiApplication.class, args);
        System.out.println("Swagger UI: "+"http://localhost:8080/api/swagger-ui/index.html");
    }


}
