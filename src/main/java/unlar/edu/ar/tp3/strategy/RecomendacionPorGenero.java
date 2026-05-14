package unlar.edu.ar.tp3.strategy;

import org.springframework.stereotype.Component;
import unlar.edu.ar.tp3.model.Cancion;

import java.util.Comparator;
import java.util.List;

@Component
public class RecomendacionPorGenero implements EstrategiaRecomendacion {

    @Override
    public List<Cancion> recomendar(List<Cancion> catalogo, Cancion base) {
        // Mismo género, ordenadas por rating
        return catalogo.stream()
                .filter(c -> !c.getId().equals(base.getId()))
                .filter(c -> c.getGenero() == base.getGenero())
                .sorted(Comparator.comparingDouble(Cancion::getRating).reversed())
                .toList();
    }
}
