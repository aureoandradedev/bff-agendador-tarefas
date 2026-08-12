# 🔀 BFF Agendador de Tarefas

Backend for Frontend (BFF) que atua como ponto de entrada unico para o sistema de agendamento de tarefas, orquestrando tres microsservicos: Usuario, Agendador de Tarefas e Notificacao.

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.5
- Spring Cloud OpenFeign
- Spring Scheduling (Cron)
- Swagger / OpenAPI (SpringDoc)
- Spring Validation
- Docker
- Docker Compose
- Lombok
- Maven
- GitHub Actions (CI)

---

## 📋 Funcionalidades

### 👤 Usuarios (proxy para microsservico Usuario)

- Cadastro, login, busca, atualizacao e exclusao de usuarios
- Gerenciamento de enderecos e telefones
- Consulta de CEP via integracao ViaCEP

### 📅 Tarefas (proxy para microsservico Agendador)

- CRUD completo de tarefas
- Busca por periodo
- Alteracao de status de notificacao

### 📧 Notificacoes (automatizado via Cron)

- Job agendado a cada 5 minutos
- Busca tarefas pendentes proximas do horario
- Envia notificacao por email automaticamente
- Atualiza status para NOTIFICADO

---

## 🔑 Endpoints

### Usuarios (`/usuario`)

| Metodo | Rota | Auth | Descricao |
|--------|------|------|-----------|
| `POST` | `/usuario` | Nao | Cadastrar usuario |
| `POST` | `/usuario/login` | Nao | Login |
| `GET` | `/usuario?email=` | Sim | Buscar usuario |
| `DELETE` | `/usuario/{email}` | Sim | Deletar usuario |
| `PUT` | `/usuario` | Sim | Atualizar usuario |
| `PUT` | `/usuario/endereco?id=` | Sim | Atualizar endereco |
| `PUT` | `/usuario/telefone?id=` | Sim | Atualizar telefone |
| `POST` | `/usuario/endereco` | Sim | Cadastrar endereco |
| `POST` | `/usuario/telefone` | Sim | Cadastrar telefone |
| `GET` | `/usuario/endereco/{cep}` | Nao | Consultar CEP |

### Tarefas (`/tarefas`)

| Metodo | Rota | Auth | Descricao |
|--------|------|------|-----------|
| `POST` | `/tarefas` | Sim | Criar tarefa |
| `GET` | `/tarefas` | Sim | Listar tarefas do usuario |
| `GET` | `/tarefas/eventos?dataInicial=&dataFinal=` | Sim | Buscar por periodo |
| `PUT` | `/tarefas?id=` | Sim | Atualizar tarefa |
| `DELETE` | `/tarefas?id=` | Sim | Deletar tarefa |
| `PATCH` | `/tarefas?status=&id=` | Sim | Alterar status |

---

## 🏗️ Arquitetura

O BFF orquestra tres microsservicos via **OpenFeign**:

```
                        ┌─ Usuario (8080) ──── PostgreSQL
                        │
Cliente ── BFF (8083) ──┼─ Agendador (8081) ── MongoDB
                        │
                        └─ Notificacao (8082) ─ SMTP
```

### Comunicacao entre servicos:

- **UsuarioClient** → microsservico de usuarios (porta 8080)
- **TarefasClient** → microsservico de tarefas (porta 8081)
- **EmailClient** → microsservico de notificacao (porta 8082)

---

## ⏰ Cron Job - Notificacoes Automaticas

O `CronService` executa a cada 5 minutos:

1. Autentica automaticamente no sistema
2. Busca tarefas com status `PENDENTE` proximas do horario
3. Envia email via microsservico de notificacao
4. Atualiza status da tarefa para `NOTIFICADO`

---

## 🐳 Docker

### Docker Compose (sistema completo)

O `docker-compose.yml` orquestra todos os microsservicos:

| Servico | Porta | Descricao |
|---------|-------|-----------|
| bff-agendador-tarefas | 8083 | Este BFF |
| usuario | 8080 | API de usuarios |
| agendador-tarefas | 8081 | API de tarefas |
| notificacao | 8084 | Servico de email |
| postgres | 5433 | Banco PostgreSQL |
| mongo | 27017 | Banco MongoDB |

### Executar o sistema completo:

```bash
docker compose up --build
```

---

## 📂 Estrutura do Projeto

```
src/main
┣ controller                    → Controllers REST
┣ business                      → Servicos e logica de negocio
┣ business/dto/in               → DTOs de entrada (requests)
┣ business/dto/out              → DTOs de saida (responses)
┣ business/enums                → Enumeracoes
┣ infrastructure/client         → Clientes Feign
┣ infrastructure/client/config  → Configuracao Feign e error decoder
┣ infrastructure/exceptions     → Excecoes customizadas
┗ infrastructure/security       → Configuracao de seguranca Swagger
```

---

## 📦 Como Executar

### Sistema completo (recomendado):

1. Clonar o projeto
```bash
git clone https://github.com/aureoandradedev/bff-agendador-tarefas.git
```

2. Entrar na pasta
```bash
cd bff-agendador-tarefas
```

3. Subir todos os servicos
```bash
docker compose up --build
```

4. Acessar Swagger
```
http://localhost:8083/swagger-ui/index.html
```

---

## 👨‍💻 Autor

**Aureo Andrade**

- GitHub: [aureoandradedev](https://github.com/aureoandradedev)
- LinkedIn: [aureoandrade](https://www.linkedin.com/in/aureoandrade/)
