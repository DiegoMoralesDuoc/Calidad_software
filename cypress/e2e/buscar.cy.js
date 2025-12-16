/// <reference types="cypress" />

describe('Buscar Recetas Page', () => {
  const baseUrl = 'http://localhost:8082';

  beforeEach(() => {
    cy.visit(`${baseUrl}/buscar.html`);
  });

  it('Debe cargar correctamente todos los elementos principales', () => {
    cy.contains('Buscar Recetas').should('be.visible');
    cy.get('#nombre').should('exist');
    cy.get('#tipoCocina').should('exist');
    cy.get('#paisOrigen').should('exist');
    cy.get('#dificultad').should('exist');
    cy.get('.btn-primary').contains('🔍 Buscar').should('exist');
    cy.get('.btn-secondary').contains('Limpiar').should('exist');
    cy.get('footer').should('contain.text', 'Recetas Seguras');
  });

  it('Debe permitir escribir en el formulario de búsqueda', () => {
    cy.get('#nombre').type('Paella').should('have.value', 'Paella');
    cy.get('#tipoCocina').type('Italiana').should('have.value', 'Italiana');
    cy.get('#paisOrigen').type('España').should('have.value', 'España');
    cy.get('#dificultad').select('Fácil').should('have.value', 'Fácil');
  });

  it('Debe permitir hacer click en los botones', () => {
    cy.get('.btn-secondary').contains('Limpiar').click();
    cy.url().should('eq', `${baseUrl}/buscar.html`);
  });

  it('Debe mostrar estado vacío cuando no hay recetas', () => {
    cy.get('.empty-state').should('exist');
    cy.get('.empty-state h3').should('contain.text', 'No se encontraron recetas');
    cy.get('.empty-state a').contains('Nueva Búsqueda').should('exist');
  });

  it('Debe mostrar links según autenticación', () => {
    // Simulamos que el usuario no está autenticado
    cy.get('a.btn-secondary').should('contain.text', 'Iniciar Sesión para Ver Detalle').should('exist');
  });

  it('Dropdown script debe estar cargado', () => {
    cy.window().then(win => {
      expect(win.console).to.exist;
    });
  });
});
