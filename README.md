# QA Lab Commerce

Aplicação completa criada para estudar, executar e demonstrar **testes de software em um cenário próximo do mercado**. O projeto simula um e-commerce com autenticação JWT, controle de acesso, catálogo, estoque, pedidos e pagamento aprovado ou recusado.

> Projeto de portfólio de **Jucelio Farias Coelho**, direcionado a oportunidades de QA / Testes de Software Júnior.


## Technical Snapshot

| Focus | Evidence in this project |
|---|---|
| Target roles | QA Engineer · QA Automation · Software Test Engineer |
| Backend under test | Java 21 · Spring Boot · Spring Security · JWT · JPA |
| Unit & integration | JUnit 5 · Mockito · Spring Boot Test · Testcontainers |
| API testing | REST Assured · Postman · Newman |
| UI automation | Playwright · Vitest · Testing Library |
| Performance & security | k6 · OWASP ZAP |
| Quality pipeline | JaCoCo · GitHub Actions · Docker Compose |

**Engineering highlights:** estratégia de testes em múltiplas camadas, automação de API e interface, testes de carga, análise de segurança, cobertura e evidências profissionais de qualidade.

**Keywords:** `Software Testing` `QA Automation` `JUnit 5` `Mockito` `REST Assured` `Playwright` `Testcontainers` `k6` `OWASP ZAP` `JaCoCo` `CI/CD`

---

## O que você pratica

- Planejamento e execução de testes manuais;
- escrita de cenários positivos, negativos, de limite e segurança;
- testes unitários com JUnit 5, Mockito, Vitest e Testing Library;
- testes de integração com Spring Boot Test, REST Assured e Testcontainers;
- automação de API com Postman e Newman;
- automação web E2E com Playwright em Chromium e Firefox;
- testes de carga e desempenho com k6;
- relatório de cobertura Java com JaCoCo;
- análise passiva de segurança com OWASP ZAP;
- pipeline CI com GitHub Actions;
- evidências e registro profissional de bugs.

## Stack

| Área | Tecnologias |
|---|---|
| Backend | Java 21, Spring Boot 3.5, Spring Security, JWT, JPA |
| Frontend | React, TypeScript, Vite |
| Banco | PostgreSQL 16 e H2 para execução simples |
| Testes Java | JUnit 5, Mockito, REST Assured, Testcontainers, JaCoCo |
| Testes web | Playwright, Vitest, Testing Library |
| API | Postman e Newman |
| Performance | Grafana k6 |
| Segurança | OWASP ZAP Baseline |
| Infraestrutura | Docker Compose e GitHub Actions |

## Arquitetura

```mermaid
flowchart TD
    U["Usuário / QA"] --> W["React Web"]
    W --> A["Spring Boot API"]
    A --> D[(PostgreSQL)]
    T["Playwright · Postman · k6"] --> W
    T --> A
```

## Telas do QA Lab Center

Os protótipos abaixo apresentam a visão planejada para o **QA Lab Center**, um painel centralizado para acompanhar testes manuais, automações, defeitos, cobertura de código e indicadores de qualidade do projeto.

### Dashboard de qualidade

Reúne os principais indicadores do projeto: testes planejados, aprovados, reprovados e bloqueados, evolução das execuções, atividades recentes e cobertura do JaCoCo. A visão geral ajuda o time a identificar rapidamente riscos e mudanças na qualidade da aplicação.

![Dashboard de qualidade do QA Lab Center](docs/images/dashboard-qualidade.jpg)

### Execução de testes

Permite executar um caso de teste passo a passo, validar pré-condições, registrar o resultado obtido e classificar cada etapa como aprovada, reprovada ou bloqueada. Também possibilita anexar evidências e abrir um bug diretamente durante a execução.

![Tela de execução de testes do QA Lab Center](docs/images/execucao-testes.jpg)

### Gestão de bugs

Centraliza os defeitos encontrados e oferece filtros por severidade, prioridade, status e módulo. Cada bug possui descrição, passos para reprodução, resultado esperado, evidências, responsável e vínculo com o caso de teste que revelou o problema.

![Tela de gestão de bugs do QA Lab Center](docs/images/gestao-bugs.jpg)

### Automação de testes

Acompanha as suítes automatizadas com JUnit, Postman/Newman, Playwright e k6, exibindo quantidade de testes, taxa de sucesso, duração e histórico de execuções. A tela também integra os resultados do pipeline CI e a cobertura de código gerada pelo JaCoCo.

![Tela de automação de testes do QA Lab Center](docs/images/automacao-testes.jpg)

### Relatórios de qualidade

Consolida os resultados dos testes, bugs por severidade, evolução da cobertura JaCoCo e qualidade por módulo. Os filtros e as opções de exportação em CSV e PDF facilitam a apresentação de evidências e o acompanhamento da evolução do projeto.

![Tela de relatórios de qualidade do QA Lab Center](docs/images/relatorios-qualidade.jpg)

## Como executar no Windows

### Opção recomendada — Docker Desktop

Pré-requisitos: Docker Desktop iniciado e Git.

No PowerShell, dentro da pasta do projeto:

```powershell
docker compose up -d --build
docker compose ps
```

Acesse:

- Aplicação: http://localhost:3000
- Swagger/OpenAPI: http://localhost:8080/swagger-ui.html
- Health check: http://localhost:8080/actuator/health

Para encerrar:

```powershell
docker compose down
```

O banco é preservado. Para apagar somente a massa local e recomeçar:

```powershell
docker compose down -v
```

### Usuários de teste

| Perfil | E-mail | Senha |
|---|---|---|
| Cliente | `qa.user@qalab.dev` | `user123` |
| Administrador | `qa.admin@qalab.dev` | `admin123` |

## Como executar os testes

### Backend, integração e JaCoCo

Requer Java 21, Maven e Docker Desktop para os testes com Testcontainers.

```powershell
cd backend
mvn clean verify
start target\site\jacoco\index.html
```

O JaCoCo gera um painel HTML com cobertura por pacote, classe, método, linha e branch. No GitHub Actions, o relatório também é publicado como artefato chamado `jacoco-report`.

### Frontend

```powershell
cd frontend
npm install
npm test
npm run build
```

### Playwright E2E

Com a aplicação iniciada:

```powershell
cd tests\e2e
npm install
npx playwright install
npm test
npm run report
```

### Postman / Newman

Importe os dois arquivos da pasta `tests/postman` no Postman e execute a coleção na ordem. Para executar com Docker:

```powershell
.\scripts\run-api-tests.ps1
```

### k6

```powershell
.\scripts\run-performance-test.ps1
```

Critérios configurados no smoke test:

- taxa de erros abaixo de 1%;
- 95% das requisições abaixo de 500 ms.

## Casos de teste manuais

A planilha CSV `tests/manual/test-cases.csv` contém 20 casos prontos, incluindo:

- login válido, inválido e campos vazios;
- autorização de cliente e administrador;
- SKU duplicado e preço inválido;
- compra sem estoque e produto inexistente;
- pagamento aprovado, recusado, repetido e de outro usuário;
- teste de contrato, segurança e performance.

Use `tests/manual/bug-report-template.md` para registrar defeitos com severidade, prioridade, passos, resultado atual, resultado esperado e evidências.

## Laboratório de bug intencional

O projeto possui uma falha controlada para praticar investigação. No PowerShell:

```powershell
$env:LAB_BUG_ALLOW_NEGATIVE_STOCK="true"
docker compose up -d --build
```

Tente comprar mais unidades do que o estoque (`TC-012`). Registre o defeito, capture request/response e logs. Depois confirme a correção:

```powershell
$env:LAB_BUG_ALLOW_NEGATIVE_STOCK="false"
docker compose up -d --build
```

## Endpoints principais

| Método | Endpoint | Regra |
|---|---|---|
| POST | `/api/auth/login` | Autenticação e emissão de JWT |
| GET | `/api/products` | Catálogo público |
| POST | `/api/products` | Somente administrador |
| POST | `/api/orders` | Cria pedido e valida estoque |
| GET | `/api/orders/mine` | Lista pedidos do usuário autenticado |
| POST | `/api/orders/{id}/payment` | Aprova ou recusa pagamento simulado |

Cartão de aprovação: `4111111111111111`. Cartão de recusa: qualquer número de 16 dígitos terminado em `0`, como `4111111111111110`. São dados fictícios e não são armazenados.

## Estrutura

```text
qa-lab-commerce/
├── backend/                 # API e testes Java
├── frontend/                # interface e testes de componente
├── tests/
│   ├── e2e/                 # Playwright
│   ├── manual/              # estratégia, casos e bugs
│   ├── performance/         # k6
│   └── postman/             # coleção e ambiente
├── scripts/                 # comandos PowerShell
├── .github/workflows/       # CI e segurança
└── docker-compose.yml
```

## Roteiro de estudo sugerido

1. Execute os 20 casos manuais e registre as evidências.
2. Explore a API no Swagger e monte requests no Postman.
3. Rode a coleção Newman e provoque falhas nas asserções.
4. Leia e altere os testes JUnit, Mockito e Vitest.
5. Automatize uma nova jornada com Playwright.
6. Ative o bug intencional, reporte e valide sua correção.
7. Rode o k6, compare p95 e taxa de erro.
8. Publique no GitHub e mostre os relatórios do pipeline.

## Como apresentar no currículo

> **QA Lab Commerce — Plataforma de Testes de Software:** desenvolvimento de ambiente full stack para testes manuais e automatizados de API, interface, integração, segurança e performance, utilizando JUnit 5, Mockito, REST Assured, Testcontainers, JaCoCo, Postman/Newman, Playwright, k6, OWASP ZAP, Docker e GitHub Actions.

## Licença

MIT — uso livre para estudo e portfólio.
