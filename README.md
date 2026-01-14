# 🔐 Spring Boot Clean Auth

Este projeto é uma implementação de referência de um **módulo de autenticação e autorização** robusto, construído seguindo rigorosamente os princípios da **Clean Architecture** e **DDD (Domain-Driven Design)**.

O objetivo é fornecer uma base segura e desacoplada para lidar com usuários, logins e proteção de rotas via **JWT**, pronta para ser integrada em aplicações maiores.

## 🎯 Funcionalidades

- [x] **Autenticação Stateless via JWT (JSON Web Token)**
- [x] **Cadastro de Usuários com Criptografia (BCrypt)**
- [x] **Arquitetura Limpa (Isolamento de Domínio)**
- [x] **Validação de Tokens e Proteção de Rotas**
- [x] **Documentação via Swagger UI**

## 🧠 Decisões Arquiteturais

Este projeto foi desenhado intencionalmente para demonstrar conhecimento avançado em Engenharia de Software. Abaixo, detalho o "porquê" de cada escolha técnica:

### 1. Clean Architecture (Arquitetura Limpa)
> **Decisão:** Isolei o núcleo da aplicação (Domain e Application) de detalhes externos (Frameworks, BD, UI).
>
> **Por quê?** Para garantir que as regras de negócio sejam o centro do software. Se amanhã eu precisar trocar o banco de dados (de SQL para Mongo) ou a biblioteca de segurança, minhas entidades e casos de uso permanecem **intactos**, sem necessidade de refatoração. O *Core* não depende do Spring; o Spring é apenas um detalhe de infraestrutura.

### 2. Domain-Driven Design (DDD) - Rich Model
> **Decisão:** Uso de *Value Objects* (como `Email`, `Password`) em vez de tipos primitivos (Strings) espalhados.
>
> **Por quê?** Para evitar o anti-pattern *Primitive Obsession*. Um e-mail não é apenas uma String; ele possui regras de formatação. Ao encapsular a validação dentro da classe `Email`, eu garanto que **é impossível** criar um e-mail inválido dentro do meu sistema, centralizando a lógica e evitando duplicação de `if`s.

### 3. Inversão de Dependência (Gateways)
> **Decisão:** Os UseCases dependem de interfaces (`UserRepository`), não de implementações (`JpaUserRepository`).
>
> **Por quê?** O Domínio precisa persistir dados, mas não deve saber *como* (SQL, JPA, Arquivo). Quem deve se adaptar às regras do Domínio é a Infraestrutura. Isso facilita absurdamente os testes unitários, pois posso "mockar" o repositório facilmente sem subir um banco de dados.

### 4. Configuração Manual de Beans
> **Decisão:** Uso de classes `@Configuration` para declarar os UseCases, em vez de anotar as classes de negócio com `@Service`.
>
> **Por quê?** Para manter o Domínio 100% agnóstico e puro (Pure Java). Se eu usar `@Service`, estou poluindo minhas regras de negócio com anotações do framework. Com a configuração manual, meu *Core* roda até fora do Spring.

### 5. Autenticação Stateless (JWT)
> **Decisão:** Uso de tokens JWT assinados em vez de sessões no servidor (Cookies/SessionID).
>
> **Por quê?** Para permitir **escalabilidade horizontal**. Como o servidor não guarda estado (quem está logado), posso ter 10 instâncias da API rodando atrás de um Load Balancer sem me preocupar em compartilhar sessões. Além disso, torna a API pronta para ser consumida nativamente por Mobile e Front-end modernos.

### 6. Java Records (DTOs)
> **Decisão:** Uso de `records` para Input/Output de dados (ex: `RegisterUserInput`).
>
> **Por quê?** Para garantir imutabilidade e reduzir verbosidade (*Boilerplate*). DTOs servem apenas para transportar dados, e Records são a maneira mais eficiente e limpa de fazer isso nas versões novas do Java.
