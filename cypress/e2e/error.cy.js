/// <reference types="cypress" />

describe('Error Page', () => {
  const baseUrl = 'http://localhost:8082';

  beforeEach(() => {
    cy.visit(`${baseUrl}/error.html`);
  });

  it('Debe cargar correctamente la página de error', () => {
    cy.contains('⚠️').should('exist');
    cy.get('.error-content h1').should('exist');
    cy.get('.error-message').should('exist');
    cy.get('.error-actions a').should('have.length', 2);
  });

  it('Debe mostrar código de error si está disponible', () => {
    cy.get('body').then($body => {
      if ($body.find('.error-code').length) {
        cy.get('.error-code strong').should('exist');
      }
    });
  });

  it('Los botones de navegación funcionan', () => {
    cy.get('.error-actions a').contains('Volver al Inicio').should('exist');
    cy.get('.error-actions a').contains('Buscar Recetas').should('exist');
  });

  it('Header y footer se muestran', () => {
    cy.get('header .logo h1').contains('Recetas Seguras').should('exist');
    cy.get('footer').should('exist');
  });
});
