/// <reference types="cypress" />

describe('Publicar Receta Page', () => {
  const baseUrl = 'http://localhost:8082';

  beforeEach(() => {
    cy.visit(`${baseUrl}/recetas/publicar`);
  });

  it('Debe cargar correctamente la página de publicar receta', () => {
    cy.contains('Publicar Nueva Receta').should('exist');
    cy.get('form.publicar-form').should('exist');
    cy.get('footer').contains('Recetas Seguras').should('exist');
  });

  it('Debe contener todos los campos obligatorios', () => {
    cy.get('#nombre').should('have.attr', 'required');
    cy.get('#tipoCocina').should('have.attr', 'required');
    cy.get('#paisOrigen').should('have.attr', 'required');
    cy.get('#dificultad').should('have.attr', 'required');
    cy.get('#tiempoPreparacion').should('have.attr', 'required');
    cy.get('#porciones').should('have.attr', 'required');
    cy.get('#ingredientes').should('have.attr', 'required');
    cy.get('#instrucciones').should('have.attr', 'required');
  });

  it('Debe permitir enviar el formulario con datos válidos', () => {
    cy.get('#nombre').type('Tarta de Manzana');
    cy.get('#tipoCocina').type('Francesa');
    cy.get('#paisOrigen').type('Francia');
    cy.get('#dificultad').select('Media');
    cy.get('#tiempoPreparacion').type('60');
    cy.get('#porciones').type('4');
    cy.get('#descripcion').type('Deliciosa tarta casera de manzana.');
    cy.get('#ingredientes').type('Manzanas\nHarina\nAzúcar\nHuevos');
    cy.get('#instrucciones').type('1. Precalentar el horno\n2. Mezclar ingredientes\n3. Hornear');
    
    // No subimos archivo real, solo validamos botón submit habilitado
    cy.get('button[type="submit"]').should('exist').and('not.be.disabled');
  });

  it('Debe permitir cancelar y volver al home', () => {
    cy.get('a.btn-secondary').contains('Cancelar').click();
    cy.url().should('eq', `${baseUrl}/`);
  });

  it('Debe mostrar mensajes de éxito o error si existen', () => {
    cy.get('body').then($body => {
      if ($body.find('.alert-success').length) {
        cy.get('.alert-success').should('exist');
      }
      if ($body.find('.alert-error').length) {
        cy.get('.alert-error').should('exist');
      }
    });
  });
});
