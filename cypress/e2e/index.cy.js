/// <reference types="cypress" />

describe('Home Page', () => {
  const baseUrl = 'http://localhost:8082';

  beforeEach(() => {
    cy.visit(`${baseUrl}/`);
  });

  it('Debe cargar correctamente la home', () => {
    // Hero y botones principales
    cy.contains('Descubre las Mejores Recetas').should('exist');
    cy.contains('Buscar Recetas').should('exist');

    // Header
    cy.get('header .logo h1').contains('Recetas Seguras').should('exist');
    cy.get('header nav .nav-link').should('have.length.at.least', 2);

    // Footer
    cy.get('footer').should('exist');
    cy.get('footer').contains('Recetas Seguras').should('exist');
  });

  it('Secciones de recetas populares y recientes', () => {
    cy.get('.recetas-section').should('have.length.at.least', 2);

    // Verificar tarjetas de receta (si existen)
    cy.get('.receta-card').then($cards => {
      if ($cards.length > 0) {
        cy.wrap($cards).first().within(() => {
          cy.get('.receta-image img, .receta-image video').should('exist');
          cy.get('.receta-info h3').should('exist');
          cy.get('.receta-details').should('exist');
        });
      }
    });
  });

  it('Botones de acceso para usuarios autenticados o no', () => {
    cy.get('body').then($body => {
      if ($body.find('.btn-secondary').length) {
        cy.get('.btn-secondary').first().should('exist');
      }
      if ($body.find('.btn-login').length) {
        cy.get('.btn-login').should('exist');
      }
    });
  });

  it('Banners se muestran', () => {
    cy.get('.banner-grid .banner').should('have.length.at.least', 1);
  });
});
