package com.duoc.recetas.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValoracionTest {

    private Valoracion valoracion;
    private Receta receta;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        valoracion = new Valoracion();
        receta = new Receta();
        receta.setId(1L);
        receta.setNombre("Receta Test");

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("user");
    }

    @Test
    void testSettersAndGetters() {
        valoracion.setId(100L);
        valoracion.setReceta(receta);
        valoracion.setUsuario(usuario);
        valoracion.setEstrellas(4);

        assertEquals(100L, valoracion.getId());
        assertEquals(receta, valoracion.getReceta());
        assertEquals(usuario, valoracion.getUsuario());
        assertEquals(4, valoracion.getEstrellas());
    }

    @Test
    void testEstrellasValidaRango() {
        valoracion.setEstrellas(1);
        assertEquals(1, valoracion.getEstrellas());

        valoracion.setEstrellas(5);
        assertEquals(5, valoracion.getEstrellas());
    }

    @Test
    void testEstrellasFueraDeRangoLanzaExcepcion() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> valoracion.setEstrellas(0));
        assertEquals("Las estrellas deben estar entre 1 y 5", ex1.getMessage());

        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> valoracion.setEstrellas(6));
        assertEquals("Las estrellas deben estar entre 1 y 5", ex2.getMessage());
    }
}
