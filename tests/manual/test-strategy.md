# Estratégia de testes — QA Lab Commerce

## Objetivo

Validar os fluxos críticos de autenticação, catálogo, estoque, pedidos e pagamentos, cobrindo funcionalidade, integração, segurança, interface e desempenho.

## Pirâmide adotada

| Camada | Ferramenta | Execução | Objetivo |
|---|---|---|---|
| Unidade | JUnit 5 + Mockito | A cada alteração | Regras isoladas e regressão rápida |
| Integração | Spring Boot Test + Testcontainers + REST Assured | Pull request | API integrada ao PostgreSQL real |
| Componente | Vitest + Testing Library | A cada alteração | Estados e interações do React |
| API | Postman + Newman | Regressão e CI | Contratos, status, payloads e encadeamento |
| E2E | Playwright | Pull request | Jornadas reais em Chromium e Firefox |
| Performance | k6 | Antes de release | Latência, taxa de erro e estabilidade |
| Segurança | OWASP ZAP Baseline | Sob demanda | Varredura passiva de riscos comuns |
| Cobertura | JaCoCo | Pipeline | Visibilidade sobre código Java exercitado |

## Critérios de entrada

- Ambiente iniciado e health check da API saudável.
- Massa de dados padrão carregada.
- Casos e critérios de aceite revisados.
- Dependências externas simuladas ou controladas.

## Critérios de saída

- Nenhum defeito blocker ou critical aberto.
- Regressão automatizada aprovada.
- Taxa de falha do smoke test abaixo de 1%.
- p95 da API de catálogo abaixo de 500 ms no ambiente local de referência.
- Evidências e relatório de execução anexados.

## Laboratório de defeito intencional

Defina `LAB_BUG_ALLOW_NEGATIVE_STOCK=true` antes de iniciar o Docker Compose. O sistema passará a aceitar pedidos acima do estoque. Execute o caso `TC-012`, registre o defeito com o modelo e depois desative a flag para confirmar a correção.
