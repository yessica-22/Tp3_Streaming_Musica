package unlar.edu.ar.tp3.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unlar.edu.ar.tp3.service.CatalogoStreamingService;

@Configuration
public class DataLoaderConfig {

    @Bean
    CommandLineRunner cargarDatosDemo(CatalogoStreamingService catalogoStreamingService) {
        return args -> {
            if (catalogoStreamingService.listarCatalogo().isEmpty()) {
                CatalogoStreamingService.conDatosDemo()
                        .listarCatalogo()
                        .forEach(catalogoStreamingService::agregarCancion);
            }
        };
    }
}
