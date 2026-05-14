package unlar.edu.ar.tp3.strategy;

import org.springframework.stereotype.Component;
import unlar.edu.ar.tp3.model.Cancion;

import java.util.Comparator;
import java.util.List;

@Component
public class RecomendacionPorPopularidad implements EstrategiaRecomendacion {

    @Override
    public List<Cancion> recomendar(List<Cancion> catalogo, Cancion base) {
        // top 5 más reproducidas global
        return catalogo.stream()
                .filter(c -> !c.getId().equals(base.getId()))
                .sorted(Comparator.comparingInt(Cancion::getReproduccionesTotales).reversed())
                .limit(5)
                .toList();
    }
}
