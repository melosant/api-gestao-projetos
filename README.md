# 🚀 API de Gestão de Projetos e Tarefas

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3+-green?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

Uma API REST completa para o gerenciamento de projetos e tarefas. Ela permite a administração de usuários, projetos e as tarefas atreladas a cada projeto, além de manter um registro de auditoria para acompanhamento das atividades. 

---

## 🛠️ Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias do ecossistema Java:

- **[Java 21](https://jdk.java.net/21/)** - Linguagem de programação moderna e performática.
- **[Spring Boot](https://spring.io/projects/spring-boot)** - Framework para construção ágil e rápida da aplicação.
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)** - Abstração sobre a persistência e manipulação de dados via Hibernate.
- **[PostgreSQL](https://www.postgresql.org/)** - Banco de dados relacional robusto.
- **[Flyway](https://flywaydb.org/)** - Versionamento e migração automatizada de banco de dados.
- **[Lombok](https://projectlombok.org/)** - Biblioteca para redução de *boilerplate code* (Getters, Setters, Builders, etc).
- **Maven** - Gerenciador de dependências e build do projeto.

---

## ⚙️ Funcionalidades Principais e Endpoints

A aplicação está configurada para rodar com o contexto `/api`. Abaixo os endpoints principais expostos pelos *Controllers*:

- 👤 **Gestão de Usuários (`/api/users`)**: Criação, atualização, busca e exclusão de usuários do sistema.
- 📁 **Gestão de Projetos (`/api/projects`)**: Controle completo sobre os projetos.
- ✅ **Gestão de Tarefas (`/api/tasks`)**: Vinculação, atualização de status e controle de tarefas associadas aos projetos e usuários.
- 🔍 **Auditoria de Tarefas (`AuditTask`)**: Mecanismo interno para manter o histórico e o rastreio das alterações nas tarefas realizadas dentro do sistema.

---

## 📦 Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:

- [Git](https://git-scm.com)
- [Java Development Kit (JDK) 21](https://jdk.java.net/21/)
- [PostgreSQL](https://www.postgresql.org/)

---

## 🏃‍♂️ Como Executar o Projeto

**1. Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/api-gestao-projetos.git
cd api-gestao-projetos
```

**2. Configure o Banco de Dados:**

Abra o arquivo `src/main/resources/application.properties` e ajuste as credenciais do PostgreSQL, caso as suas credenciais locais sejam diferentes:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/project_manager
spring.datasource.username=postgres
spring.datasource.password=senhaPostgres
```

**Importante:** Crie um banco de dados chamado `project_manager` no seu servidor PostgreSQL local (ex: via pgAdmin ou linha de comando) antes de rodar a aplicação. Na primeira execução, o **Flyway** cuidará de criar as tabelas e relacionamentos necessários automaticamente.

**3. Compile e rode a aplicação:**

Utilize o Maven Wrapper que já vem incluso no projeto. No diretório raiz do projeto, execute o comando:

No Windows:
```cmd
mvnw.cmd spring-boot:run
```

No Linux/Mac:
```bash
./mvnw spring-boot:run
```

Após o build e a inicialização, a API estará disponível localmente no endereço: `http://localhost:8080/api`

---

## 🗄️ Estrutura do Banco de Dados e Migrações

A base de dados é estruturada e versionada via **Flyway** (`spring.flyway.enabled=true`).
Durante a inicialização da aplicação, o Flyway lerá as *migrations* localizadas internamente (normalmente em `src/main/resources/db/migration`) e aplicará automaticamente qualquer modificação pendente no banco. O JPA está configurado como `validate` para garantir que o código reflita a estrutura criada pelo Flyway.

---

<p align="center">
  Criado para facilitar a gestão de projetos e o controle de tarefas.
</p>
