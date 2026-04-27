package unlar.edu.ar.tp3.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unlar.edu.ar.tp3.model.Cancion;
import unlar.edu.ar.tp3.model.Genero;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogoStreamingServiceTest {

    private CatalogoStreamingService service;

    @BeforeEach
    void setUp() {
        service = new CatalogoStreamingService();
        service.agregarCancion(new Cancion("A", "Artista 1", "Alb1", Genero.ROCK, 180, 50, 4.3, LocalDate.of(2015, 1, 1)));
        service.agregarCancion(new Cancion("B", "Artista 1", "Alb2", Genero.ROCK, 240, 200, 4.8, LocalDate.of(2018, 1, 1)));
        service.agregarCancion(new Cancion("C", "Artista 2", "Alb3", Genero.POP, 300, 500, 4.9, LocalDate.of(2020, 1, 1)));
        service.agregarCancion(new Cancion("D", "Artista 3", "Alb4", Genero.JAZZ, 120, 20, 3.9, LocalDate.of(1999, 1, 1)));
    }

    @Test
    void filtrarCanciones_compuestoFunciona() {
        List<Cancion> resultado = service.filtrarCanciones(Genero.ROCK, "artista 1", 2010, 2019, 4.5);
        assertEquals(1, resultado.size());
        assertEquals("B", resultado.getFirst().getTitulo());
    }

    @Test
    void top10MasReproducidas_ordenCorrecto() {
        List<Cancion> top = service.top10MasReproducidas();
        assertEquals("C", top.getFirst().getTitulo());
        assertEquals("B", top.get(1).getTitulo());
    }

    @Test
    void promedioDuracionPorGenero_calculaPromedio() {
        Map<Genero, Double> promedios = service.promedioDuracionPorGenero();
        assertEquals(210.0, promedios.get(Genero.ROCK));
    }

    @Test
    void artistaMasPopular_tomaSumaDeReproducciones() {
        String artista = service.artistaMasPopular().orElseThrow();
        assertEquals("Artista 2", artista);
    }

    @Test
    void distribucionPorDecadas_agruparCorrectamente() {
        Map<Integer, List<Cancion>> distribucion = service.distribucionPorDecadas();
        assertTrue(distribucion.containsKey(2010));
        assertTrue(distribucion.containsKey(2020));
        assertTrue(distribucion.containsKey(1990));
    }

    @Test
    void playlistAutomaticaExacta_devuelveCombinacionExacta() {
        List<Cancion> playlist = service.playlistAutomaticaExacta(5);
        int total = playlist.stream().mapToInt(Cancion::getDuracionSegundos).sum();
        assertEquals(300, total);
    }

    @Test
    void playlistAutomaticaExacta_vaciaSiNoHayCombinacion() {
        List<Cancion> playlist = service.playlistAutomaticaExacta(13);
        assertTrue(playlist.isEmpty());
    }
}
