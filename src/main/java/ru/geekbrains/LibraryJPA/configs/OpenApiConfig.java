package ru.geekbrains.LibraryJPA.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Библиотека",
                description = "Библиотека", version = "1.0.0",
                contact = @Contact(
                        name = "Alexei"
                )
        )
)
public class OpenApiConfig {
}
