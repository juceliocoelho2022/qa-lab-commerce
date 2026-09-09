$ErrorActionPreference = "Stop"
$ProjectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $ProjectRoot

docker compose up -d --build
docker run --rm `
  -v "${PWD}/tests/postman:/etc/newman" `
  postman/newman:alpine run /etc/newman/QA-Lab.postman_collection.json `
  -e /etc/newman/QA-Lab.postman_environment.json `
  --env-var "baseUrl=http://host.docker.internal:8080"
