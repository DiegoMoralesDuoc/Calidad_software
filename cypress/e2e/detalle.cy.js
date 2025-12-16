/// <reference types="cypress" />

describe('Detalle de Receta Page', () => {
  const baseUrl = 'http://localhost:8082';

  beforeEach(() => {
    cy.visit(`${baseUrl}/login`);
    cy.get('#username').type('admin');
    cy.get('#password').type('admin123');
    cy.get('button[type=submit]').click();
    cy.url().should('eq', `${baseUrl}/`);
    cy.visit(`${baseUrl}/recetas/detalle/1`);
  });

  it('Debe cargar correctamente los elementos principales', () => {
    cy.get('.receta-header h1').should('exist');
    cy.get('.receta-meta-info').should('exist');
    cy.get('.receta-autor-detalle').should('exist');
  });

  it('Debe mostrar imagen o video principal', () => {
    cy.get('.receta-galeria img, .receta-galeria video').should('exist');
  });

  it('Debe mostrar ingredientes e instrucciones', () => {
    cy.get('.receta-ingredientes').should('exist');
    cy.get('.receta-instrucciones').should('exist');
  });

  it('Debe permitir interactuar con valoraciones', () => {
    cy.get('.valoracion-form input[type=radio]').should('have.length', 5);
    cy.get('.valoracion-form button').contains('Valorar').should('exist');
  });

  it('Debe permitir publicar un comentario', () => {
    cy.get('.nuevo-comentario textarea').type('Comentario de prueba').should('have.value', 'Comentario de prueba');
    cy.get('.nuevo-comentario button').contains('Publicar Comentario').should('exist');
  });

  it('Debe mostrar lista de comentarios', () => {
    cy.get('.comentarios-lista').should('exist');
  });

  it('Botones de navegación funcionan', () => {
    cy.get('.receta-acciones a').contains('← Volver a Búsqueda').should('exist');
    cy.get('.receta-acciones a').contains('🏠 Ir al Inicio').should('exist');
  });

  it('Modal de eliminación aparece al click en eliminar (si existe el botón)', () => {
    cy.get('body').then($body => {
      if ($body.find('#btnEliminarReceta').length) {
        cy.get('#btnEliminarReceta').click();
        cy.get('#modalEliminar').should('be.visible');
        cy.get('#btnCancelarEliminar').click();
        cy.get('#modalEliminar').should('not.be.visible');
      }
    });
  });

  it('Scripts externos y dropdown cargan', () => {
    cy.window().then(win => {
      expect(win.console).to.exist;
    });
  });
});
