package unlar.edu.ar.tp3.controllers.dto;

import unlar.edu.ar.tp3.model.Cancion;
import unlar.edu.ar.tp3.model.Genero;

import java.time.LocalDate;

// DTO para devolver la cancion en la API sin acoplar directamente
// la entidad interna a la respuesta HTTP.
public record CancionResponse(
        String id,
        String titulo,
        String artista,
        String album,
        Genero genero,
        int duracionSegundos,
        int reproducciones,
        double rating,
        LocalDate fechaLanzamiento
) {
    public static CancionResponse from(Cancion cancion) {
        return new CancionResponse(
                cancion.getId(),
                cancion.getTitulo(),
                cancion.getArtista(),
                cancion.getAlbum(),
                cancion.getGenero(),
                cancion.getDuracionSegundos(),
                cancion.getReproduccionesTotales(),
                cancion.getRating(),
                cancion.getFechaLanzamiento()
        );
    }
}
