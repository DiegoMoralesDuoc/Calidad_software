/// <reference types="cypress" />

describe('Login Page', () => {
  const baseUrl = 'http://localhost:8082';
  beforeEach(() => {
    cy.visit(`${baseUrl}/login.html`);
  });

  it('Debe cargar correctamente todos los elementos', () => {
    cy.contains('Iniciar Sesión').should('be.visible');
    cy.get('#username').should('exist');
    cy.get('#password').should('exist');
    cy.get('button[type=submit]').should('exist');
    cy.get('input[name="_csrf"]').should('exist');
    cy.get('.login-footer a').should('contain.text', '← Volver al inicio');
    cy.get('.test-users').should('contain.text', 'admin');
    cy.get('.security-info').should('contain.text', '🔒 Conexión segura');
  });

  it('Debe permitir login exitoso con usuario válido', () => {
    cy.get('#username').type('admin');
    cy.get('#password').type('admin123');
    cy.get('button[type=submit]').click();

    // Comprueba redirección al home
    cy.url().should('eq', `${baseUrl}/`);
  });

  it('Debe mostrar mensaje de error con login inválido', () => {
    cy.get('#username').type('usuarioX');
    cy.get('#password').type('wrongpass');
    cy.get('button[type=submit]').click();

    cy.get('.alert-error').should('be.visible')
      .and('contain.text', 'Error de autenticación');
  });

  it('Debe permitir navegar al inicio desde el link', () => {
    cy.get('.login-footer a').click();
    cy.url().should('eq', `${baseUrl}/`);
  });

  it('Debe tener CSRF token presente', () => {
    cy.get('input[name="_csrf"]').should('have.attr', 'value').and('not.be.empty');
  });
});
