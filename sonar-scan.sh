#!/bin/bash

echo "=========================================="
echo "🔍 EJECUTANDO ANÁLISIS SONARQUBE DESDE HOST"
echo "=========================================="

# Comando exitoso de Maven ejecutado directamente en el host
mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=app_recetas_v3 \
  -Dsonar.projectName='app_recetas_v3' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sqp_fca0510ea2e4c07d69c1b8a7e028d90849189dcb

if [ $? -eq 0 ]; then
    echo ""
    echo "=========================================="
    echo "✅ ANÁLISIS COMPLETADO (Verifica Cobertura en SonarQube)"
    echo "=========================================="
    echo ""
    echo "📊 Ver resultados en: http://localhost:9000/dashboard?id=app_recetas_v3"
    echo ""
else
    echo ""
    echo "❌ Error en el análisis. Intenta reiniciar SonarQube."
    echo ""
fi