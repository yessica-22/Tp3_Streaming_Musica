package unlar.edu.ar.tp3.strategy;

import unlar.edu.ar.tp3.model.Cancion;
import java.util.List;

public interface EstrategiaRecomendacion {
    List<Cancion> recomendar(List<Cancion> catalogo, Cancion base);
}
