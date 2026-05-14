package unlar.edu.ar.tp3.service;

import org.springframework.stereotype.Service;
import unlar.edu.ar.tp3.model.Cancion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ConcurrenciaService {

    private final CatalogoStreamingService catalogoService;

    public ConcurrenciaService(CatalogoStreamingService catalogoService) {
        this.catalogoService = catalogoService;
    }

    public String simularReproduccionesConcurrentes(String cancionId, int numUsuarios) {
        Cancion cancion = catalogoService.buscarPorId(cancionId)
                .orElseThrow(() -> new IllegalArgumentException("Cancion no encontrada"));

        int reproduccionesIniciales = cancion.getReproduccionesTotales();
        
        // Simulación Secuencial para medir tiempo
        long startSecuencial = System.currentTimeMillis();
        for (int i = 0; i < numUsuarios; i++) {
            simularRetardoReproduccion();
            cancion.incrementarReproducciones();
        }
        long timeSecuencial = System.currentTimeMillis() - startSecuencial;
        
        // Reset para la simulación concurrente
        cancion.getReproducciones().set(reproduccionesIniciales);

        // Simulación Concurrente
        ExecutorService executor = Executors.newFixedThreadPool(5);
        long startConcurrente = System.currentTimeMillis();

        for (int i = 0; i < numUsuarios; i++) {
            executor.submit(() -> {
                simularRetardoReproduccion();
                cancion.incrementarReproducciones();
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long timeConcurrente = System.currentTimeMillis() - startConcurrente;

        return String.format("Simulación para %d usuarios terminada.\n" +
                "Reproducciones finales: %d (Esperado: %d)\n" +
                "Tiempo Secuencial: %d ms\n" +
                "Tiempo Concurrente: %d ms",
                numUsuarios,
                cancion.getReproduccionesTotales(),
                reproduccionesIniciales + numUsuarios,
                timeSecuencial,
                timeConcurrente);
    }

    private void simularRetardoReproduccion() {
        try {
            // Simulamos trabajo para que se note la diferencia de tiempo
            Thread.sleep(100); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
