package unlar.edu.ar.tp3.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Cancion {
    private final String id;
    private final String titulo;
    private final String artista;
    private final String album;
    private final Genero genero;
    private final int duracionSegundos;
    private final AtomicInteger reproducciones;
    private final double rating;
    private final LocalDate fechaLanzamiento;

    public Cancion(
            String titulo,
            String artista,
            String album,
            Genero genero,
            int duracionSegundos,
            int reproduccionesIniciales,
            double rating,
            LocalDate fechaLanzamiento
    ) {
        this(UUID.randomUUID().toString(), titulo, artista, album, genero, duracionSegundos, reproduccionesIniciales, rating, fechaLanzamiento);
    }

    public Cancion(
            String id,
            String titulo,
            String artista,
            String album,
            Genero genero,
            int duracionSegundos,
            int reproduccionesIniciales,
            double rating,
            LocalDate fechaLanzamiento
    ) {
        this.id = Objects.requireNonNull(id, "id no puede ser null");
        this.titulo = Objects.requireNonNull(titulo, "titulo no puede ser null");
        this.artista = Objects.requireNonNull(artista, "artista no puede ser null");
        this.album = Objects.requireNonNull(album, "album no puede ser null");
        this.genero = Objects.requireNonNull(genero, "genero no puede ser null");
        this.fechaLanzamiento = Objects.requireNonNull(fechaLanzamiento, "fechaLanzamiento no puede ser null");

        if (duracionSegundos <= 0) {
            throw new IllegalArgumentException("duracionSegundos debe ser mayor a 0");
        }
        if (reproduccionesIniciales < 0) {
            throw new IllegalArgumentException("reproduccionesIniciales no puede ser negativo");
        }
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("rating debe estar entre 0.0 y 5.0");
        }

        this.duracionSegundos = duracionSegundos;
        this.reproducciones = new AtomicInteger(reproduccionesIniciales);
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getAlbum() {
        return album;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public AtomicInteger getReproducciones() {
        return reproducciones;
    }

    public int getReproduccionesTotales() {
        return reproducciones.get();
    }

    public double getRating() {
        return rating;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public int incrementarReproducciones() {
        return reproducciones.incrementAndGet();
    }
}
