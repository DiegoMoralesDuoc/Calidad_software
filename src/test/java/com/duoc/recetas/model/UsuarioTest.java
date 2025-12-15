package com.duoc.recetas.model;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    /**
     * Prueba los constructores, getters y setters (métodos generados por @Data, @NoArgsConstructor y @AllArgsConstructor).
     */
    @Test
    void testConstructoresYAccessors() {
        // 1. Mock de dependencias para el constructor AllArgsConstructor
        Set<Rol> rolesMock = new HashSet<>();
        
        // 2. Test del Constructor con todos los argumentos (@AllArgsConstructor)
        Usuario usuarioCompleto = new Usuario(
            1L, 
            "jefe_chef", 
            "hashedpassword123", 
            "Diego Morales", 
            "diego@duoc.cl", 
            true, 
            rolesMock
        );

        // Verificación de Getters del constructor completo
        assertEquals(1L, usuarioCompleto.getId());
        assertEquals("jefe_chef", usuarioCompleto.getUsername());
        assertEquals("hashedpassword123", usuarioCompleto.getPassword());
        assertEquals("Diego Morales", usuarioCompleto.getNombreCompleto());
        assertTrue(usuarioCompleto.getEnabled());
        assertEquals(rolesMock, usuarioCompleto.getRoles());

        // 3. Test del Constructor vacío (@NoArgsConstructor) y Setters
        Usuario usuarioVacio = new Usuario();
        usuarioVacio.setId(2L);
        usuarioVacio.setEmail("otro@duoc.cl");
        usuarioVacio.setEnabled(false);
        
        // Verificación de Setters y valores por defecto
        assertEquals(2L, usuarioVacio.getId());
        assertEquals("otro@duoc.cl", usuarioVacio.getEmail());
        assertFalse(usuarioVacio.getEnabled());
        assertNotNull(usuarioVacio.getRoles()); // Los roles por defecto deben ser un HashSet no nulo
    }
    
    /**
     * Prueba el método utilitario agregarRol().
     */
    @Test
    void testAgregarRol() {
        Usuario usuario = new Usuario();
        Rol rolChef = new Rol();
        rolChef.setNombre("ROLE_CHEF");
        
        assertEquals(0, usuario.getRoles().size());
        
        usuario.agregarRol(rolChef);
        
        assertEquals(1, usuario.getRoles().size());
        assertTrue(usuario.getRoles().contains(rolChef));
    }

    /**
     * Prueba el método toString() (generado por @Data).
     */
    @Test
    void testToString() {
        Usuario usuario = new Usuario();
        usuario.setUsername("admin_test");
        
        String result = usuario.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("username=admin_test"));
    }
}