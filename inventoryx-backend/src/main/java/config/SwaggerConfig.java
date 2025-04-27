package config;  // Keeping your original package name

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI inventoryXOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("InventoryX API")
                                .description("API for Inventory Management System")
                                .version("v1.0")
                                .contact(new Contact()
                                        .name("InventoryX Support")
                                        .email("support@inventoryx.com"))
                                .license(new License()
                                        .name("Apache 2.0")
                                        .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}