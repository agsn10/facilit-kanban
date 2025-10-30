# 🧩 Desafio — Gestão de Projetos Kanban

## 📖 Introdução

Este projeto foi desenvolvido como parte de um **desafio técnico de arquitetura**, com o objetivo de implementar uma **API para gestão de projetos via Kanban**, utilizando **princípios de DDD (Domain-Driven Design)** e **arquitetura hexagonal (Ports & Adapters)**.

A abordagem escolhida tem como foco **isolar o domínio da aplicação** e **garantir alta testabilidade, baixo acoplamento e facilidade de evolução**.  
A arquitetura hexagonal foi adotada para **organizar os fluxos de entrada e saída** de forma clara, separando as responsabilidades entre o **núcleo do domínio**, os **adaptadores de entrada (Primary Ports)** e os **adaptadores de saída (Secondary Ports)**.

---

## 🧩 Abordagem Escolhida

A implementação segue a **Arquitetura Hexagonal (Ports & Adapters)** com base em **DDD** e **Spring Boot**.  
A separação entre **camadas de aplicação, domínio e infraestrutura** garante:

- 🔄 Independência do framework e do banco de dados
- 🧠 Clareza nas regras de negócio
- 🧪 Facilidade de testes unitários e integração
- 🚀 Evolução modular (possibilidade de virar microserviço)

---

## 🧭 Arquitetura Hexagonal — Ports & Adapters

Na arquitetura hexagonal, o termo **PPI** significa:

> ✅ **Primary Ports and Interfaces** (ou **Portas e Interfaces Primárias**)

Essas portas representam **os pontos de entrada do sistema**, ou seja, **quem inicia a execução de um caso de uso**.  
São as interfaces que **exponibilizam as operações do domínio** para o mundo externo (REST API, CLI, Mensageria, etc).

---

### 🔹 Estrutura das Portas

| Tipo de Porta | Nome | Fluxo | Função |
|----------------|------|--------|---------|
| **Primária** | ✅ **PPI** | Entrada → Domínio | Quem inicia o caso de uso. Representa a API da aplicação. |
| **Secundária** | **SPI** | Domínio → Saída | Quem a aplicação usa para acessar serviços externos (DB, API externa, etc). |

Assim, o **domínio nunca depende das tecnologias externas**.  
A inversão de dependência garante que o **núcleo da aplicação** permaneça estável, mesmo que os adaptadores (banco de dados, REST controllers, etc.) mudem.

---

## 🧠 Definição do Bounded Context

Com base nos conceitos de **DDD**, o desafio propõe um **domínio central de Gestão de Projetos via Kanban**, que pode ser decomposto em **três contextos principais**, conforme o escopo funcional.

---

### 📌 **Bounded Context Principal**

| Bounded Context | Entidades | Responsabilidades |
|-----------------|------------|-------------------|
| **Projeto** | `Projeto` | Regras de Kanban, cálculos, indicadores |
| **Pessoas** | `Responsável` | Cadastro e vínculo com projeto |
| **Organizacional** | `Secretaria` | Agrupar responsáveis e projetos |

**Observação:** Esse contexto **não controla o projeto**, apenas se relaciona a ele.

---

## 🔗 Comunicação entre Contextos

- **Projeto referencia Responsável**, mas **Responsável não referencia Projeto**
- **Evita acoplamento circular**
- Facilita uma futura **extração de microserviços** (ex: serviço de identidade/pessoas)
- Mantém a independência entre módulos

---

### 📂 Estrutura sugerida de pastas

| Camada                 | O que faz                             | Depende de           |
| ---------------------- | ------------------------------------- | -------------------- |
| `domain`               | Regras, entidades, portas             | Nenhuma externa      |
| `application/usecases` | Orquestra as regras e fluxo das ações | Domain (ports)       |
| `infrastructure`       | REST, Banco, Docker… o mundo real     | Domain & Application |
| `shared`               | Cross-cutting (exceções, util…)       | livre                |


---

### Modelo Entidade Relacional

![Modelo Entidade Relacional](/img/diagrama_er.png)

### Open API

Acesso: https://localhost:8043/swagger-ui.html

![Swagger](/img/openapi.png)



