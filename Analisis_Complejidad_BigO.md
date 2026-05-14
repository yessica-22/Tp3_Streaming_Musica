# Análisis de Complejidad - TP3 Streaming Música

## 1. Búsqueda Binaria por Título

**Algoritmo implementado en:** `BusquedaOrdenaminetoService.busquedaBinariaPorTitulo`

```java
public Optional<Cancion> busquedaBinariaPorTitulo(String titulo) {
    List<Cancion> ordenadas = catalogoService.listarCatalogo().stream()
            .sorted(Comparator.comparing(Cancion::getTitulo, String.CASE_INSENSITIVE_ORDER))
            .toList();

    int inicio = 0;
    int fin = ordenadas.size() - 1;

    while (inicio <= fin) {
        int medio = inicio + (fin - inicio) / 2;
        // ... comparaciones y división ...
    }
}
```

### Análisis:
- **Ordenamiento Inicial:** Para poder realizar una búsqueda binaria, la colección debe estar ordenada. En el código, se ordena la lista entera mediante `stream().sorted()`, lo cual tiene una complejidad de **O(n log n)** donde `n` es el número de canciones.
- **Búsqueda Binaria:** Una vez que la lista está ordenada, el bucle `while` divide el espacio de búsqueda a la mitad en cada iteración. La complejidad temporal del ciclo de búsqueda en sí es **O(log n)**.
- **Complejidad Temporal Total:** Dado que el ordenamiento toma la mayor parte del tiempo, la complejidad total de esta función, tal como está escrita, es **O(n log n)**. Si la lista ya estuviera pre-ordenada globalmente (y solo pasáramos la lista pre-ordenada al método sin reordenarla), el tiempo sería puramente **O(log n)**.
- **Complejidad Espacial:** **O(n)** porque se crea una nueva lista `ordenadas` con los elementos clonados.

---

## 2. Playlist Automática (Problema de la Mochila)

**Algoritmo implementado en:** `CatalogoStreamingService.playlistAutomaticaExacta` / `resolverMochila`

```java
private boolean resolverMochila(int indice, int restanteSegundos, List<Cancion> actual, List<Cancion> resultado) {
    if (restanteSegundos == 0) return true;
    if (restanteSegundos < 0 || indice >= catalogo.size()) return false;
    
    // Incluir elemento
    if (resolverMochila(indice + 1, restanteSegundos - candidata.getDuracionSegundos(), actual, resultado)) return true;
    
    // No incluir elemento
    return resolverMochila(indice + 1, restanteSegundos, actual, resultado);
}
```

### Análisis:
Este algoritmo utiliza recursión pura probando dos caminos para cada canción: **incluirla** o **no incluirla**. Es una solución exacta (Subset Sum) mediante Backtracking sin memoización.

- **Complejidad Temporal:** Cada canción tiene 2 opciones. Por lo tanto, el árbol de recursión tiene una profundidad máxima de `n` (cantidad total de canciones) y en cada nivel se ramifica en 2. En el peor de los casos (donde no se encuentra solución temprana), la complejidad temporal es **O(2^n)**. Es un algoritmo de tiempo exponencial, lo que significa que para catálogos muy grandes (ej. n > 40), se volverá muy ineficiente e inejecutable en tiempo real.
- **Complejidad Espacial:** La profundidad máxima de la pila de llamadas recursivas es `n`. Además, mantenemos una lista `actual` y `resultado` que a lo sumo contienen `n` elementos. Por lo tanto, la complejidad espacial es **O(n)**.
