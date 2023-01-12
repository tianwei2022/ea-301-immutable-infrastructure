package org.example.looam.web.config;

import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Value("${swagger-ui.server.url}")
  private String serverUrl;

  @Value("${swagger-ui.title:swagger-ui}")
  private String title;

  @Bean
  public OpenAPI customizeOpenAPI() {
    OpenAPI openAPI = new OpenAPI().info(new Info().title(title));
    if (serverUrl != null) {
      Server server = new Server();
      server.setUrl(serverUrl);
      openAPI.servers(List.of(server));
    }
    return openAPI;
  }
}
