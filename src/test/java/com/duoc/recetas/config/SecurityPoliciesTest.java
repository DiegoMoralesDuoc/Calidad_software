package com.duoc.recetas.config;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityPoliciesTest {

    /**
     * Prueba FINAL para cubrir el constructor privado, esperando la excepción
     * envuelta que lanza el constructor modificado.
     */
    @Test
    void cannotInstantiate() throws NoSuchMethodException {
        Constructor<SecurityPolicies> constructor = SecurityPolicies.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        
        // Esperamos que el constructor lance una excepción de InvocationTargetException, 
        // ya que el constructor ahora lanza UnsupportedOperationException.
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
    
    /**
     * Prueba para asegurar que todas las constantes de políticas están definidas y no vacías.
     */
    @Test
        void policiesAreDefined() {
            // Cubre la línea de definición de CONTENT_SECURITY_POLICY
            assertNotNull(SecurityPolicies.CONTENT_SECURITY_POLICY, "CSP no debe ser nula");
            assertTrue(SecurityPolicies.CONTENT_SECURITY_POLICY.contains("default-src 'self'"), "CSP debe tener contenido básico");
            
            // Cubre la línea de definición de PERMISSIONS_POLICY
            assertNotNull(SecurityPolicies.PERMISSIONS_POLICY, "Permissions Policy no debe ser nula");
            assertTrue(SecurityPolicies.PERMISSIONS_POLICY.contains("camera=()"), "Permissions Policy debe tener contenido básico");
            
            // Cubre la línea de definición de EXTENDED_PERMISSIONS_POLICY
            assertNotNull(SecurityPolicies.EXTENDED_PERMISSIONS_POLICY, "Extended Permissions Policy no debe ser nula");
            assertTrue(SecurityPolicies.EXTENDED_PERMISSIONS_POLICY.contains("payment=()"), "Extended Permissions Policy debe tener contenido extendido");

            // Cubre la línea de definición de REFERRER_POLICY
            assertNotNull(SecurityPolicies.REFERRER_POLICY, "Referrer Policy no debe ser nula");
            // CORRECCIÓN: Usar assertEquals según la recomendación de SonarQube.
            assertEquals("no-referrer", SecurityPolicies.REFERRER_POLICY, "Referrer Policy debe ser 'no-referrer'"); 
        }
}