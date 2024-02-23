# Fun Mode Child - E-commerce API

Este projeto é uma API REST robusta para um e-commerce de produtos infantis, desenvolvida com **Spring Boot 3** e **MongoDB**. A API gerencia usuários, autenticação JWT, catálogo de produtos, categorias e um fluxo completo de pedidos com controle de estoque e situação.

## 🚀 Tecnologias e Ferramentas

- **Linguagem:** Kotlin (JVM 21)
- **Framework:** Spring Boot 3.2.2
- **Banco de Dados:** MongoDB
- **Segurança:** Spring Security + JWT (JSON Web Token)
- **Documentação:** SpringDoc OpenAPI (Swagger UI)
- **Testes:** JUnit 5 + MockK + Spring MockK
- **Build:** Gradle (Kotlin DSL)
- **Infra:** Docker & Docker Compose
- **Interface Web:** React + Vite + TypeScript + Tailwind CSS

## 🏗️ Arquitetura e Estrutura

O projeto segue o padrão **MVC (Model-View-Controller)** adaptado para uma arquitetura de serviços:

- `controller`: Endpoints REST e documentação Swagger.
- `service`: Regras de negócio, controle de estoque e fluxos complexos.
- `repository`: Interface de comunicação com o MongoDB.
- `entity`: Modelos de dados do banco de dados.
- `model`: DTOs (Data Transfer Objects) para requisições e respostas.
- `config`: Configurações de Segurança, JWT e Tratamento Global de Erros.
- `frontend`: Aplicação Web desenvolvida com React e Vite.

## 🔄 Fluxos Principais

### 1. Autenticação e Autorização
- **Perfis:** `ROLE_CUSTOMER` (padrão) e `ROLE_ADMIN`.
- **JWT:** As permissões do usuário são embutidas no token para controle de acesso stateless.
- **Segurança:** Operações de escrita em produtos e categorias são restritas a administradores.

### 2. Fluxo de Pedidos
- **Criação:** Verifica a disponibilidade de estoque antes de confirmar o pedido.
- **Estoque:** Baixa automática de itens no estoque no momento da criação.
- **Situação:** Ciclo de vida completo: `PENDING` -> `PAID` -> `SHIPPED` -> `DELIVERED` ou `CANCELLED`.

### 3. Carrinho de Compras
- **Persistência:** O carrinho é armazenado no backend vinculado ao ID do usuário.
- **TTL (Time To Live):** Itens são mantidos por **24 horas** após a última atualização.
- **Gestão:** Endpoints para adicionar, remover e listar itens, com resolução detalhada de produtos.

### 4. Pagamento (Simulação)
- Ao processar um pagamento com sucesso, a situação do pedido é automaticamente atualizado para `PAID`.

## 🛠️ Como Usar

### Pré-requisitos
- Docker e Docker Compose instalados.
- Node.js (v18 ou superior) e npm instalados.

### Execução Local

#### 1. Backend (API)
1. Suba o banco de dados MongoDB via Docker:
   ```bash
   docker-compose up -d
   ```
2. Execute a aplicação:
   ```bash
   ./gradlew bootRun
   ```

Acesse a documentação Swagger em: `http://localhost:8080/api/swagger-ui/index.html`

#### 2. Frontend (Interface Web)
1. Navegue até o diretório do frontend:
   ```bash
   cd frontend
   ```
2. Instale as dependências:
   ```bash
   npm install
   ```
3. Inicie o servidor de desenvolvimento:
   ```bash
   npm run dev
   ```
4. Acesse a aplicação em: `http://localhost:5173` (ou a URL indicada no terminal).

> **Nota:** O frontend está configurado para fazer proxy das requisições `/api` para o backend rodando em `localhost:8080`. Certifique-se de que o backend esteja ativo.

## 🧪 Processos de Build e Teste

### Compilação
```bash
./gradlew classes
```

### Executar Testes
```bash
./gradlew test
```

### Gerar Artefato (JAR)
```bash
./gradlew bootJar
```

## 🛡️ Segurança e Tratamento de Erros
- **Global Error Handler:** Respostas JSON padronizadas para erros (400, 404, 500, etc).
- **Validação:** Todos os inputs são validados via Bean Validation (`@Valid`).
- **Logs:** Monitoramento de operações críticas via SLF4J.
