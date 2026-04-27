package unlar.edu.ar.tp3.service;

import org.springframework.stereotype.Service;
import unlar.edu.ar.tp3.model.Cancion;
import unlar.edu.ar.tp3.model.Genero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogoStreamingService {
    private final List<Cancion> catalogo = new ArrayList<>();

    public void agregarCancion(Cancion cancion) {
        catalogo.add(Objects.requireNonNull(cancion));
    }

    public List<Cancion> listarCatalogo() {
        return Collections.unmodifiableList(catalogo);
    }

    public Optional<Cancion> buscarPorId(String id) {
        return catalogo.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public List<Cancion> buscarPorTituloYArtista(String titulo, String artista) {
        return catalogo.stream()
                .filter(c -> titulo == null || titulo.isBlank() || c.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .filter(c -> artista == null || artista.isBlank() || c.getArtista().toLowerCase().contains(artista.toLowerCase()))
                .toList();
    }

    public Optional<Cancion> incrementarReproducciones(String id) {
        return buscarPorId(id)
                .map(cancion -> {
                    cancion.incrementarReproducciones();
                    return cancion;
                });
    }

    // 2.2 - Filtrado compuesto por genero, artista, rango de anios y rating minimo.
    public List<Cancion> filtrarCanciones(
            Genero genero,
            String artista,
            Integer anioDesde,
            Integer anioHasta,
            Double ratingMinimo
    ) {
        return catalogo.stream()
                .filter(c -> genero == null || c.getGenero() == genero)
                .filter(c -> artista == null || artista.isBlank() || c.getArtista().equalsIgnoreCase(artista))
                .filter(c -> anioDesde == null || c.getFechaLanzamiento().getYear() >= anioDesde)
                .filter(c -> anioHasta == null || c.getFechaLanzamiento().getYear() <= anioHasta)
                .filter(c -> ratingMinimo == null || c.getRating() >= ratingMinimo)
                .toList();
    }

    // 2.2 - Top 10 mas reproducidas.
    public List<Cancion> top10MasReproducidas() {
        return catalogo.stream()
                .sorted(Comparator.comparingInt(Cancion::getReproduccionesTotales).reversed())
                .limit(10)
                .toList();
    }

    // 2.2 - Promedio de duracion por genero.
    public Map<Genero, Double> promedioDuracionPorGenero() {
        return catalogo.stream()
                .collect(Collectors.groupingBy(Cancion::getGenero, Collectors.averagingInt(Cancion::getDuracionSegundos)));
    }

    // 2.2 - Artista mas popular por suma de reproducciones.
    public Optional<String> artistaMasPopular() {
        return catalogo.stream()
                .collect(Collectors.groupingBy(Cancion::getArtista, Collectors.summingInt(Cancion::getReproduccionesTotales)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // 2.2 - Distribucion por decadas.
    public Map<Integer, List<Cancion>> distribucionPorDecadas() {
        return catalogo.stream()
                .collect(Collectors.groupingBy(c -> (c.getFechaLanzamiento().getYear() / 10) * 10));
    }

    // 2.2 - Playlist automatica: aproximacion de mochila (subset sum exacto).
    public List<Cancion> playlistAutomaticaExacta(int minutosObjetivo) {
        if (minutosObjetivo <= 0) {
            return List.of();
        }

        int objetivoSegundos = minutosObjetivo * 60;
        List<Cancion> resultado = new ArrayList<>();
        boolean encontrada = resolverMochila(0, objetivoSegundos, new ArrayList<>(), resultado);
        return encontrada ? resultado : List.of();
    }

    private boolean resolverMochila(
            int indice,
            int restanteSegundos,
            List<Cancion> actual,
            List<Cancion> resultado
    ) {
        if (restanteSegundos == 0) {
            resultado.clear();
            resultado.addAll(actual);
            return true;
        }

        if (restanteSegundos < 0 || indice >= catalogo.size()) {
            return false;
        }

        Cancion candidata = catalogo.get(indice);

        actual.add(candidata);
        if (resolverMochila(indice + 1, restanteSegundos - candidata.getDuracionSegundos(), actual, resultado)) {
            return true;
        }
        actual.remove(actual.size() - 1);

        return resolverMochila(indice + 1, restanteSegundos, actual, resultado);
    }

    public static CatalogoStreamingService conDatosDemo() {
        CatalogoStreamingService service = new CatalogoStreamingService();
        service.agregarCancion(new Cancion("Paranoid Android", "Radiohead", "OK Computer", Genero.ROCK, 386, 4500, 4.9, LocalDate.of(1997, 5, 21)));
        service.agregarCancion(new Cancion("Get Lucky", "Daft Punk", "Random Access Memories", Genero.ELECTRONICA, 369, 7000, 4.8, LocalDate.of(2013, 4, 19)));
        service.agregarCancion(new Cancion("Take Five", "Dave Brubeck", "Time Out", Genero.JAZZ, 324, 2200, 4.7, LocalDate.of(1959, 7, 1)));
        service.agregarCancion(new Cancion("Billie Jean", "Michael Jackson", "Thriller", Genero.POP, 294, 9200, 4.9, LocalDate.of(1983, 1, 2)));
        service.agregarCancion(new Cancion("Clair de Lune", "Debussy", "Suite bergamasque", Genero.CLASICA, 300, 1300, 4.6, LocalDate.of(1905, 1, 1)));
        service.agregarCancion(new Cancion("Bad Guy", "Billie Eilish", "When We All Fall Asleep, Where Do We Go?", Genero.POP, 194, 8100, 4.5, LocalDate.of(2019, 3, 29)));
        service.agregarCancion(new Cancion("Around the World", "Daft Punk", "Homework", Genero.ELECTRONICA, 431, 3900, 4.4, LocalDate.of(1997, 3, 17)));
        service.agregarCancion(new Cancion("Bohemian Rhapsody", "Queen", "A Night at the Opera", Genero.ROCK, 354, 15000, 5.0, LocalDate.of(1975, 10, 31)));
        return service;
    }
}
