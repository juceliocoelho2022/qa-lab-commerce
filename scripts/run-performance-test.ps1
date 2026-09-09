$ErrorActionPreference = "Stop"
$ProjectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $ProjectRoot

docker compose up -d --build
docker run --rm -i `
  -v "${PWD}/tests/performance:/scripts" `
  -e "BASE_URL=http://host.docker.internal:8080" `
  grafana/k6:latest run /scripts/smoke.js
