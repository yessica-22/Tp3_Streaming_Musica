package unlar.edu.ar.tp3.strategy;

import org.springframework.stereotype.Component;
import unlar.edu.ar.tp3.model.Cancion;

import java.time.LocalDate;
import java.util.List;

@Component
public class RecomendacionDescubrimiento implements EstrategiaRecomendacion {

    @Override
    public List<Cancion> recomendar(List<Cancion> catalogo, Cancion base) {
        // menos de 1000 reproducciones, fecha reciente (< 2 años), género diferente al habitual
        LocalDate haceDosAnios = LocalDate.now().minusYears(2);
        
        return catalogo.stream()
                .filter(c -> !c.getId().equals(base.getId()))
                .filter(c -> c.getReproduccionesTotales() < 1000)
                .filter(c -> c.getFechaLanzamiento().isAfter(haceDosAnios))
                .filter(c -> c.getGenero() != base.getGenero())
                .toList();
    }
}
