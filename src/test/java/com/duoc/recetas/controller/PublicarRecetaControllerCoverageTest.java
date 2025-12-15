package com.duoc.recetas.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.duoc.recetas.model.Receta;
import com.duoc.recetas.model.Usuario;
import com.duoc.recetas.repository.UsuarioRepository;
import com.duoc.recetas.service.RecetaService;

@ExtendWith(MockitoExtension.class)
class PublicarRecetaControllerCoverageTest {

    @Mock
    private RecetaService recetaService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PublicarRecetaController controller;

    private UserDetails buildUser() {
        return new User("user", "pwd", java.util.List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    // ================== FORMULARIO ==================
    @Test
    void mostrarFormularioDebeRetornarVistaConTitulo() {
        Model model = new ExtendedModelMap();

        String view = controller.mostrarFormulario(model);

        assertEquals("publicar-receta", view);
        assertEquals("Publicar Nueva Receta", model.getAttribute("titulo"));
    }

    // ================== PUBLICAR RECETA ==================
    @Test
    void publicarRecetaUsuarioNoEncontradoRedirige() {
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.empty());
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.publicarReceta("n", "t", "p", "d", 1, "ing", "inst", "desc", 1, null, buildUser(), redirect);

        assertEquals("redirect:/recetas/publicar", view);
        assertTrue(redirect.getFlashAttributes().containsKey("mensajeError"));
    }

    @Test
    void publicarRecetaExitosaRedirigeADetalle() {
        Usuario usuario = new Usuario();
        usuario.setUsername("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        Receta guardada = new Receta();
        guardada.setId(99L);
        when(recetaService.guardarReceta(any())).thenReturn(guardada);

        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.publicarReceta("n", "t", "p", "d", 10, "ing", "inst", "desc", 2, null, buildUser(), redirect);

        assertEquals("redirect:/recetas/detalle/99", view);
        assertTrue(redirect.getFlashAttributes().containsKey("mensajeExito"));
    }

    @Test
    void publicarRecetaConArchivoInvalidoLanzaErrorYCaeEnCatch() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        MultipartFile badFile = new org.springframework.mock.web.MockMultipartFile("media", "doc.txt", "text/plain", "x".getBytes());
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.publicarReceta("n", "t", "p", "d", 10, "ing", "inst", "desc", 2,
                new MultipartFile[]{badFile}, buildUser(), redirect);

        assertEquals("redirect:/recetas/publicar", view);
        assertTrue(redirect.getFlashAttributes().containsKey("mensajeError"));
    }

    @Test
    void publicarRecetaConArchivoGrande() {
        Usuario usuario = new Usuario();
        usuario.setUsername("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        byte[] largeContent = new byte[11 * 1024 * 1024]; // 11MB
        MultipartFile largeFile = new org.springframework.mock.web.MockMultipartFile("media", "grande.jpg", "image/jpeg", largeContent);
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.publicarReceta("n", "t", "p", "d", 10, "ing", "inst", "desc", 2,
                new MultipartFile[]{largeFile}, buildUser(), redirect);

        assertEquals("redirect:/recetas/publicar", view);
        assertTrue(redirect.getFlashAttributes().containsKey("mensajeError"));
    }

    @Test
    void publicarRecetaConArchivoSinNombre() {
        Usuario usuario = new Usuario();
        usuario.setUsername("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        MultipartFile fileNoName = new org.springframework.mock.web.MockMultipartFile("media", null, "image/jpeg", "contenido".getBytes());
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.publicarReceta("Receta", "Tipo", "País", "Fácil", 20,
                "ingredientes", "instrucciones", "desc", 4,
                new MultipartFile[]{fileNoName}, buildUser(), redirect);

        assertEquals("redirect:/recetas/publicar", view);
        assertTrue(redirect.getFlashAttributes().containsKey("mensajeError"));
    }

    @Test
    void publicarRecetaConVariosArchivos() {
        Usuario usuario = new Usuario();
        usuario.setUsername("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        MultipartFile file1 = new org.springframework.mock.web.MockMultipartFile("media1", "foto1.jpg", "image/jpeg", "contenido1".getBytes());
        MultipartFile file2 = new org.springframework.mock.web.MockMultipartFile("media2", "foto2.png", "image/png", "contenido2".getBytes());
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.publicarReceta("Receta", "Tipo", "País", "Fácil", 20,
                "ingredientes", "instrucciones", "desc", 4,
                new MultipartFile[]{file1, file2}, buildUser(), redirect);

        // Los archivos no se guardan realmente en tests unitarios
        assertEquals("redirect:/recetas/publicar", view);
        assertTrue(redirect.getFlashAttributes().containsKey("mensajeError"));
    }

    @Test
    void publicarRecetaConArchivoVideo() {
        Usuario usuario = new Usuario();
        usuario.setUsername("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        MultipartFile videoFile = new org.springframework.mock.web.MockMultipartFile("media", "video.mp4", "video/mp4", "video content".getBytes());
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.publicarReceta("Receta", "Tipo", "País", "Fácil", 20,
                "ingredientes", "instrucciones", "desc", 4,
                new MultipartFile[]{videoFile}, buildUser(), redirect);

        assertEquals("redirect:/recetas/publicar", view);
        assertTrue(redirect.getFlashAttributes().containsKey("mensajeError"));
    }

    // ================== MÉTODOS PRIVADOS ==================
    @Test
    void esArchivoValidoYDeterminarTipoMediaPorReflection() throws Exception {
        Method esArchivoValido = PublicarRecetaController.class.getDeclaredMethod("esArchivoValido", String.class);
        esArchivoValido.setAccessible(true);
        Method determinarTipo = PublicarRecetaController.class.getDeclaredMethod("determinarTipoMedia", String.class);
        determinarTipo.setAccessible(true);

        assertFalse((Boolean) esArchivoValido.invoke(controller, (Object) null));
        assertTrue((Boolean) esArchivoValido.invoke(controller, "image/png"));
        assertTrue((Boolean) esArchivoValido.invoke(controller, "video/mp4"));
        assertTrue((Boolean) esArchivoValido.invoke(controller, "image/jpeg"));
        assertTrue((Boolean) esArchivoValido.invoke(controller, "image/gif"));
        assertTrue((Boolean) esArchivoValido.invoke(controller, "image/webp"));
        assertTrue((Boolean) esArchivoValido.invoke(controller, "video/webm"));
        assertTrue((Boolean) esArchivoValido.invoke(controller, "video/ogg"));
        assertFalse((Boolean) esArchivoValido.invoke(controller, "application/pdf"));

        assertEquals("image", determinarTipo.invoke(controller, (Object) null));
        assertEquals("video", determinarTipo.invoke(controller, "video/webm"));
        assertEquals("video", determinarTipo.invoke(controller, "video/mp4"));
        assertEquals("image", determinarTipo.invoke(controller, "image/png"));
    }
}
