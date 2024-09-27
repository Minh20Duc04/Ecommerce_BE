package com.Ecommerce_BE.Security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customSwagger()
    {
        Contact contact = new Contact();
        contact.setName("NguyenMinhDuc");
        contact.setUrl("https://www.facebook.com/profile.php?id=100022697329247");
        contact.setEmail("nguyenminhduc02042004@gmail.com");

        License license = new License()
                .name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0");


        Info info = new Info()
                .contact(contact)
                .license(license)
                .title("Ecommerce API")
                .version("1.0.0")
                .description("Document for Ecommerce API");

        return new OpenAPI().info(info);
    }

}
