$ErrorActionPreference = "Stop"
$ProjectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $ProjectRoot

Write-Host "Iniciando QA Lab Commerce..." -ForegroundColor Cyan
docker compose up -d --build

Write-Host "Aplicacao: http://localhost:3000" -ForegroundColor Green
Write-Host "Swagger:   http://localhost:8080/swagger-ui.html" -ForegroundColor Green
Write-Host "API health: http://localhost:8080/actuator/health" -ForegroundColor Green
