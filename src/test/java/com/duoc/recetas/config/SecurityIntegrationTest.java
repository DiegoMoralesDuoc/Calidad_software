package com.duoc.recetas.config;

import com.duoc.recetas.model.Receta;
import com.duoc.recetas.repository.UsuarioRepository;
import com.duoc.recetas.service.ComentarioService;
import com.duoc.recetas.service.RecetaService;
import com.duoc.recetas.service.ValoracionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecetaService recetaService;

    @MockBean
    private ComentarioService comentarioService;

    @MockBean
    private ValoracionService valoracionService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    // --- URLs privadas con usuario logueado ---
    @Test
    @WithMockUser
    void privateUrlsAccessibleWithUser() throws Exception {
        Receta recetaMock = new Receta();
        recetaMock.setId(1L);
        recetaMock.setNombre("Receta de prueba");
        recetaMock.setDificultad("Fácil");
        recetaMock.setTiempoPreparacion(30);
        recetaMock.setDescripcion("Descripción de prueba");
        recetaMock.setFechaCreacion(LocalDateTime.now());

        when(recetaService.obtenerRecetaPorId(1L)).thenReturn(Optional.of(recetaMock));

        mockMvc.perform(get("/recetas/detalle/1")).andExpect(status().isOk());
        mockMvc.perform(get("/recetas/publicar")).andExpect(status().isOk()); // sin "/1" porque GET /publicar no recibe id
    }

    // --- URLs privadas sin usuario logueado ---
    @Test
    void privateUrlsRedirectToLoginWithoutUser() throws Exception {
        mockMvc.perform(get("/recetas/detalle/1")).andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/recetas/publicar")).andExpect(status().is3xxRedirection());
    }
}
