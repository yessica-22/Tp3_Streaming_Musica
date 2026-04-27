package unlar.edu.ar.tp3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unlar.edu.ar.tp3.controllers.dto.CancionResponse;
import unlar.edu.ar.tp3.service.CatalogoStreamingService;

import java.util.List;

@RestController
@RequestMapping("/api/canciones")
public class CancionController {

    private final CatalogoStreamingService catalogoStreamingService;

    public CancionController(CatalogoStreamingService catalogoStreamingService) {
        this.catalogoStreamingService = catalogoStreamingService;
    }

    @GetMapping
    public ResponseEntity<List<CancionResponse>> listarCanciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<CancionResponse> canciones = catalogoStreamingService.listarCatalogo().stream()
                .skip((long) page * size)
                .limit(size)
                .map(CancionResponse::from)
                .toList();

        return ResponseEntity.ok(canciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CancionResponse> buscarPorId(@PathVariable String id) {
        return catalogoStreamingService.buscarPorId(id)
                .map(CancionResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CancionResponse>> buscarFiltrada(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista
    ) {
        List<CancionResponse> resultado = catalogoStreamingService.buscarPorTituloYArtista(titulo, artista).stream()
                .map(CancionResponse::from)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/{id}/reproducir")
    public ResponseEntity<CancionResponse> reproducir(@PathVariable String id) {
        return catalogoStreamingService.incrementarReproducciones(id)
                .map(CancionResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
