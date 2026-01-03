# Sistema Bancário Simplificado - Core de Domínio

Projeto desenvolvido como desafio técnico para demonstrar boas práticas de arquitetura de software, modelagem de domínio e escrita de código limpo em Java puro.

O objetivo não é simular uma aplicação completa com API REST ou persistência, mas construir um **núcleo de domínio sólido**, isolado de infraestrutura, onde as regras de negócio são o centro de tudo.

## Problema Proposto

Construir o core de um sistema bancário simples para uma fintech, com as seguintes operações principais:

- Criar conta
- Depósito
- Saque
- Transferência entre contas (atômica)
- Bloquear/desbloquear conta
- Consultar conta e histórico de operações

O foco está na consistência das regras de negócio, encapsulamento do domínio, clareza do código e testes que validam comportamento real.

## Arquitetura Adotada

O projeto segue os princípios da **Clean Architecture** de forma leve e pragmática, com separação clara de responsabilidades:

```
src/main/java/
├── domain/          → Entidades, value objects e enums do domínio
├── service/         → Lógica de negócio e casos de uso
├── repository/      → Interface e implementação in-memory
├── usecase/         → Orquestração dos fluxos de entrada/saída
├── dto/             → Objetos de transferência (entrada e saída)
├── mapper/          → Conversão entre domínio e DTOs
├── exception/       → Exceções específicas do domínio
└── util/            → Utilitários auxiliares
```

### Princípios aplicados

- **Camadas bem definidas**: nenhuma camada acessa diretamente uma camada mais interna sem passar pela correta.
- **Domínio protegido**: saldo e histórico só são alterados por métodos comportamentais. Não há setters públicos para campos críticos.
- **Imutabilidade externa**: o histórico de operações é retornado como visão imutável.
- **Regra de negócio no lugar certo**: todas as validações (saldo insuficiente, conta bloqueada, valor inválido etc.) estão no domínio ou service.
- **Sem vazamento de abstrações**: entidades de domínio nunca são expostas — sempre retornamos DTOs.

## Decisões de Design

| Decisão                              | Justificativa                                                                              |
| ------------------------------------ | ------------------------------------------------------------------------------------------ |
| Sem frameworks (Spring, Lombok etc.) | Manter o foco total no domínio e nas regras de negócio, sem dependências externas          |
| Repositório in-memory                | Isolar o domínio de infraestrutura de persistência, facilitando testes e clareza           |
| Sem mocks nos testes                 | Priorizar testes de comportamento real (estado final), mais confiáveis neste contexto      |
| Transferência atômica sem transações | Validação completa antes de qualquer alteração — simples, clara e suficiente para o escopo |
| Exceções customizadas                | Comunicam claramente violações de regras de negócio (não erros técnicos)                   |
| Uso de records (Java 17)             | Para representar operações do histórico de forma imutável e expressiva                     |

## Funcionalidades Implementadas

- Criação de conta com validação de nome
- Depósito e saque com regras de valor e status
- Transferência entre contas com:
  - Validação prévia completa
  - Atomicidade lógica
  - Registro detalhado no histórico de ambas as contas (enviada/recebida)
- Bloqueio e desbloqueio de conta com impacto em todas as operações
- Consulta de conta e histórico completo (ordenado por data)
- Tratamento claro de erros via exceções de domínio

## Testes

Os testes foram escritos com JUnit 5 puro, com foco em **comportamento**:

- Cenários de sucesso e falha para todas as operações
- Validação da atomicidade da transferência (nada muda se validação falhar)
- Garantia de que conta bloqueada rejeita operações corretamente
- Imutabilidade do histórico
- Funcionamento correto do mapper (ida e volta)
- Fluxos completos (usecase → service → repository)

## Como Executar

Requisitos:

- JDK 11 ou superior
- Maven (opcional, mas recomendado)

```bash
# Clonar o repositório
git clone <url-do-seu-repositorio>

# Executar os testes
./mvnw test    # ou: mvn test

# Compilar
./mvnw compile
```

## Reflexões Finais

Este projeto foi pensado para ser **extensível**:

- Para adicionar persistência real, bastaria implementar a interface do repositório com JPA ou outro mecanismo.
- Para expor via API, os use cases poderiam ser chamados por controllers Spring.
- Para adicionar auditoria, notificações ou logs, poderíamos injetar serviços nas camadas adequadas.

O mais importante aqui não é a complexidade técnica, mas a **clareza, consistência e proteção do domínio** — características que tornam um sistema mantenível a longo prazo.

Feedbacks, sugestões e discussões técnicas são muito bem-vindos!

---
