package unlar.edu.ar.tp3.service;

import org.springframework.stereotype.Service;
import unlar.edu.ar.tp3.model.Cancion;
import unlar.edu.ar.tp3.model.Genero;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BusquedaOrdenaminetoService {

    private final CatalogoStreamingService catalogoService;

    public BusquedaOrdenaminetoService(CatalogoStreamingService catalogoService) {
        this.catalogoService = catalogoService;
    }

    // Búsqueda binaria por título: lista pre-ordenada con Comparator.naturalOrder()
    public Optional<Cancion> busquedaBinariaPorTitulo(String titulo) {
        List<Cancion> ordenadas = catalogoService.listarCatalogo().stream()
                .sorted(Comparator.comparing(Cancion::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .toList();

        int inicio = 0;
        int fin = ordenadas.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Cancion cancionMedio = ordenadas.get(medio);
            int comparacion = cancionMedio.getTitulo().compareToIgnoreCase(titulo);

            if (comparacion == 0) {
                return Optional.of(cancionMedio);
            } else if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return Optional.empty();
    }

    // Ordenamiento personalizado: artista, luego fecha (reversed)
    public List<Cancion> ordenamientoPersonalizado() {
        return catalogoService.listarCatalogo().stream()
                .sorted(Comparator.comparing(Cancion::getArtista)
                        .thenComparing(Cancion::getFechaLanzamiento)
                        .reversed())
                .toList();
    }

    // Búsqueda lineal con predicados múltiples (género AND año > X AND rating > Y)
    public List<Cancion> busquedaLinealMultiple(Genero genero, int anioMayorA, double ratingMayorA) {
        return catalogoService.listarCatalogo().stream()
                .filter(c -> c.getGenero() == genero)
                .filter(c -> c.getFechaLanzamiento().getYear() > anioMayorA)
                .filter(c -> c.getRating() > ratingMayorA)
                .toList();
    }
}
