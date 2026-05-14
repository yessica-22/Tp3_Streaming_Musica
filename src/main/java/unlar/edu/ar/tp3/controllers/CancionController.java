package unlar.edu.ar.tp3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlar.edu.ar.tp3.controllers.dto.CancionResponse;
import unlar.edu.ar.tp3.model.Cancion;
import unlar.edu.ar.tp3.model.Genero;
import unlar.edu.ar.tp3.service.BusquedaOrdenaminetoService;
import unlar.edu.ar.tp3.service.CatalogoStreamingService;
import unlar.edu.ar.tp3.service.ConcurrenciaService;
import unlar.edu.ar.tp3.strategy.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/canciones")
public class CancionController {

    private final CatalogoStreamingService catalogoStreamingService;
    private final BusquedaOrdenaminetoService busquedaOrdenaminetoService;
    private final ConcurrenciaService concurrenciaService;
    
    // Strategies
    private final RecomendacionPorGenero recomendacionPorGenero;
    private final RecomendacionPorPopularidad recomendacionPorPopularidad;
    private final RecomendacionDescubrimiento recomendacionDescubrimiento;

    public CancionController(
            CatalogoStreamingService catalogoStreamingService,
            BusquedaOrdenaminetoService busquedaOrdenaminetoService,
            ConcurrenciaService concurrenciaService,
            RecomendacionPorGenero recomendacionPorGenero,
            RecomendacionPorPopularidad recomendacionPorPopularidad,
            RecomendacionDescubrimiento recomendacionDescubrimiento
    ) {
        this.catalogoStreamingService = catalogoStreamingService;
        this.busquedaOrdenaminetoService = busquedaOrdenaminetoService;
        this.concurrenciaService = concurrenciaService;
        this.recomendacionPorGenero = recomendacionPorGenero;
        this.recomendacionPorPopularidad = recomendacionPorPopularidad;
        this.recomendacionDescubrimiento = recomendacionDescubrimiento;
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
    
    @GetMapping("/buscar-binaria")
    public ResponseEntity<CancionResponse> buscarBinaria(@RequestParam String titulo) {
        return busquedaOrdenaminetoService.busquedaBinariaPorTitulo(titulo)
                .map(CancionResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/reproducir")
    public ResponseEntity<CancionResponse> reproducir(@PathVariable String id) {
        return catalogoStreamingService.incrementarReproducciones(id)
                .map(CancionResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/reproducir-concurrente")
    public ResponseEntity<String> reproducirConcurrente(@PathVariable String id, @RequestParam(defaultValue = "10") int usuarios) {
        try {
            String resultado = concurrenciaService.simularReproduccionesConcurrentes(id, usuarios);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Estadisticas ---
    @GetMapping("/top10")
    public ResponseEntity<List<CancionResponse>> top10() {
        return ResponseEntity.ok(catalogoStreamingService.top10MasReproducidas().stream().map(CancionResponse::from).toList());
    }
    
    @GetMapping("/promedio-duracion")
    public ResponseEntity<Map<Genero, Double>> promedioDuracion() {
        return ResponseEntity.ok(catalogoStreamingService.promedioDuracionPorGenero());
    }
    
    @GetMapping("/artista-popular")
    public ResponseEntity<String> artistaPopular() {
        return catalogoStreamingService.artistaMasPopular()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- Playlist ---
    @GetMapping("/playlist-automatica")
    public ResponseEntity<List<CancionResponse>> playlistAutomatica(@RequestParam int minutos) {
        return ResponseEntity.ok(catalogoStreamingService.playlistAutomaticaExacta(minutos).stream().map(CancionResponse::from).toList());
    }

    // --- Recomendaciones ---
    @GetMapping("/{id}/recomendar")
    public ResponseEntity<List<CancionResponse>> recomendar(
            @PathVariable String id,
            @RequestParam String estrategia
    ) {
        Optional<Cancion> cancionOpt = catalogoStreamingService.buscarPorId(id);
        if (cancionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Cancion base = cancionOpt.get();
        EstrategiaRecomendacion est;
        
        switch (estrategia.toLowerCase()) {
            case "genero": est = recomendacionPorGenero; break;
            case "popularidad": est = recomendacionPorPopularidad; break;
            case "descubrimiento": est = recomendacionDescubrimiento; break;
            default: return ResponseEntity.badRequest().build();
        }
        
        List<CancionResponse> recomendadas = est.recomendar(catalogoStreamingService.listarCatalogo(), base)
                .stream().map(CancionResponse::from).toList();
                
        return ResponseEntity.ok(recomendadas);
    }
}
