package com.duoc.recetas.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RecetaTest {

    private final LocalDateTime now = LocalDateTime.now().minusDays(1);

    /**
     * Prueba los constructores, getters y setters (métodos generados por @Data, @NoArgsConstructor y @AllArgsConstructor).
     */
    @Test
    void testConstructoresYAccessors() {
        // Mock de dependencias (para evitar NullPointerException al llamar al constructor AllArgsConstructor)
        Usuario mockAutor = mock(Usuario.class);
        List<RecetaMedia> mockMediaFiles = new ArrayList<>();

        // 1. Test del Constructor con todos los argumentos (@AllArgsConstructor)
        Receta recetaCompleta = new Receta(
            1L, 
            "Lasaña Clásica", 
            "Italiana", 
            "Italia", 
            "Intermedio", 
            60, 
            "Carne, pasta, tomate", 
            "Pasos de la lasaña...", 
            "url_foto", 
            "image", 
            "Una deliciosa lasaña", 
            6, 
            true, 
            false, 
            now, 
            500,
            mockAutor, 
            mockMediaFiles
        );

        // Verificación de Getters del constructor completo
        assertEquals(1L, recetaCompleta.getId());
        assertEquals("Lasaña Clásica", recetaCompleta.getNombre());
        assertEquals("Italiana", recetaCompleta.getTipoCocina());
        assertEquals("Intermedio", recetaCompleta.getDificultad());
        assertEquals(60, recetaCompleta.getTiempoPreparacion());
        assertTrue(recetaCompleta.getPopular());
        assertFalse(recetaCompleta.getReciente());
        assertEquals(now, recetaCompleta.getFechaCreacion());
        assertEquals(mockAutor, recetaCompleta.getAutor());
        assertEquals(mockMediaFiles, recetaCompleta.getMediaFiles());

        // 2. Test del Constructor vacío (@NoArgsConstructor) y Setters
        Receta recetaVacia = new Receta();
        Long nuevoId = 2L;
        recetaVacia.setId(nuevoId);
        recetaVacia.setNombre("Torta de Chocolate");
        recetaVacia.setVisualizaciones(100);
        
        // Verificación de Setters y valores por defecto
        assertEquals(nuevoId, recetaVacia.getId());
        assertEquals("Torta de Chocolate", recetaVacia.getNombre());
        assertEquals(100, recetaVacia.getVisualizaciones());
        assertEquals("image", recetaVacia.getMediaType()); // Valor por defecto
        assertFalse(recetaVacia.getPopular()); // Valor por defecto
        
        // Verificación del método @PrePersist (onCreate)
        recetaVacia.onCreate();
        assertNotNull(recetaVacia.getFechaCreacion());
    }

    /**
     * Prueba el método toString() (generado por @Data)
     */
    @Test
    void testToString() {
        Receta receta = new Receta();
        receta.setNombre("Ensalada César");
        receta.setId(5L);
        
        String result = receta.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("id=5"));
        assertTrue(result.contains("nombre=Ensalada César"));
    }
}