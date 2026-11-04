## B1: DESAFIO 1

## PROBLEMA

O MVP de gamificação evolui de aproximadamente 1.000 usuários para um cenário de até 100.000 usuários. Ranking,
cursos, conquistas e recompensas permanecem no Core, mas o Ranking tende a crescer mais que as demais
capacidades. A decisão arquitetural não deve partir da tecnologia desejada, e sim dos drivers que realmente
justificam mudança estrutural.

Organizei em três tabelas Markdown, mantendo o conteúdo original e corrigindo pequenos erros de escrita.

## RFs afetados

| ID   | Requisito funcional | Justificativa                                                                                                                                                                                                                                                              |
|------|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| RF01 | Ranking             | A funcionalidade de ranking tende a receber um volume maior de acessos em relação às demais funcionalidades da aplicação. Portanto, necessita de escalabilidade funcional e arquitetônica.                                                                                 |
| RF02 | Conquistas          | As funcionalidades relacionadas a conquistas e outras recompensas tendem a evoluir juntamente com a gamificação da plataforma, necessitando de escalabilidade funcional e comunicação efetiva com o ranking, uma vez que a pontuação é proveniente dessas funcionalidades. |

### RNFs — Atributos de qualidade significativos (ASRs)

| Aprovação | ID    | Atributo de qualidade    | Justificativa                                                                                                                                                              |
|-----------|-------|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ✅        | RNF01 | Manutenibilidade         | Como o sistema pretende crescer de forma geral, é importante que a arquitetura seja facilmente expansível, permitindo a adição de novas funcionalidades e regras.          |
| ✅        | RNF02 | Escalabilidade           | A previsão é que o ranking receba uma carga maior de usuários. Portanto, é importante que ele possa escalar horizontalmente quando necessário.                             |
| ✅        | RNF03 | Complexidade operacional | A equipe é reduzida e não possui experiência prévia com arquiteturas modulares ou microsserviços, o que aumenta a dificuldade de implementação e operação dessas soluções. |
| ❎        | RNF04 | Desempenho               | Não há evidências de deficiência de desempenho no cenário atual, embora esse atributo continue sendo relevante.                                                            |
| ❎        | RNF05 | Testabilidade            | É relevante, mas não deve se sobrepor às prioridades arquiteturais do cenário atual, no qual a testabilidade não está sendo prejudicada.                                   |

## RPCs — Restrições e premissas

| ID    | Restrição ou premissa                 | Descrição                                                                                                                               |
|-------|---------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| RPC01 | Core atual em Java/Spring Boot        | O núcleo atual do projeto deve ser mantido para evitar retrabalho desnecessário.                                                        |
| RPC02 | Equipe de 3 desenvolvedores           | A equipe é reduzida e não possui experiência prévia com arquiteturas complexas, como microsserviços.                                    |
| RPC03 | Banco de dados relacional já modelado | O banco PostgreSQL já está modelado, e não há evidências que justifiquem sua substituição.                                              |
| RPC04 | Legado da AC1                         | O domínio existente deve ser preservado, evitando retrabalho e priorizando a evolução incremental, respeitando o princípio Open/Closed. |

## Alternativas e Trade-Offs

| Arquitetura         | Vantagens                                                                                                              | Desvantagens                                                                                                                  | Avaliação de implementação                                                                                                                                                 |
|---------------------|------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Monólito em Camadas | Baixíssima complexidade operacional; Não há necessida de alterar a arquitetura existente;                              | Risco de aumento de acoplamento, causando problemas de escalabilidade e testabilidade                                         | É possível manter, mas dado o cenário de escalabilidade, pode não ser interessante mantê-lo, além de dificultar uma futura possibilidade de implementação de microsserviço |
| Monólito Modular    | Diminui acoplamento, facilitando escalabilidade, testes e manutenção; Facilita uma futura migração para microsserviços | Os serviços ainda não são totalmente independentes; Complexidade de implementação baixa, mas existente, no contexto da equipe | Melhor possibilidade para o cenário atual, que pede escalabilidade individual, sem retrabalho excessivo                                                                    |
| Microsserviços      | Serviços totalmente independentes, facilitando deploy, testes, manutenção e escala                                     | Altíssimo custo e complexidade de operação e infraestrutura; Migração complexa exigindo muito retrabalho                      | Para o cenário atual, não há necessidade da implementação do mesmo, que levaria ao overengineering                                                                         |

## Decisão do Tech Lead

> Dadas as necessidades expostas, de escalabilidade funcional, mas não de desempenho; E dadas as limitações técnicas da
> equipe, que envolvem limitações de custo operacional e habilidades; Optou-se por seguir com a implementação da
> arquitetura **monolítica modular**, evoluindo a arquitetura monolítica em camadas. A arquitetura monolítica modular
> apresenta a menor complexidade operacional e melhor atende as necessidades do cenário apresentado. Essa evolução
> mantém
> o core em Spring Boot, separando as funcionalidades em módulos, possibilitando escalar as funcionalidades necessárias
> sem a necessidade ratrabalho, e com o menor custo operacional e de infraestrutura. A implementação de microsserviços
> só
> virá a ser cogitada quando houver uma necessidade real de escalabilidade horizontal de uma determinada funcionalidade.

## ADR-001 — Evolução do Core de Monólito em Camadas para Monólito Modular

| Campo                       | Registro                                                                                                                                                                                                                                             |
|:----------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Título**                  | ADR-001 — Evoluir o Core de Monólito em Camadas para Monólito Modular                                                                                                                                                                                |
| **Contexto**                | O produto cresceu para um cenário de até 100.000 usuários; o Ranking apresenta tendência de crescimento de carga superior às demais capacidades; e as funcionalidades de Cursos, Conquistas e Recomendações continuam evoluindo em ritmos distintos. |
| **ASRs**                    | Manutenibilidade; Escalabilidade; Complexidade operacional.                                                                                                                                                                                          |
| **RPCs**                    | Core atual em Java/Spring Boot; Equipe reduzida (3 desenvolvedores); Banco de dados relacional existente; Legado preservado da AC1.                                                                                                                  |
| **Alternativas**            | A) Monólito em Camadas; B) Monólito Modular; C) Decomposição seletiva em serviços (Microsserviços).                                                                                                                                                  |
| **Decisão**                 | Adotar o **Monólito Modular**, organizando o Core por capacidades de negócio (Ranking, Cursos, Conquistas, Recompensas) e mantendo a fronteira do Ranking explicitamente isolada para eventual extração futura.                                      |
| **Consequências positivas** | Melhor separação de responsabilidades; redução de acoplamento estrutural; facilidade de manutenção e testes por módulo; preservação da simplicidade de deploy unificado para a capacidade atual da equipe.                                           |
| **Trade-offs**              | O deploy ainda permanece conjunto; o processo de execução é compartilhado; o módulo de Ranking ainda não possui escala de infraestrutura independente neste estágio.                                                                                 |
| **Gatilho de revisão**      | Reavaliar a extração do módulo de Ranking para um serviço independente apenas quando métricas de produção demonstrarem gargalo de desempenho, necessidade de escala isolada ou frequência de deploy distinta.                                        |

---

## C4 — Visão de Container (Desafio 1)

Neste desafio, a decisão macro mantém o Core como um único container Spring Boot, porém explicitando que sua organização interna passa a ser modular por funcionalidade. Abaixo, o diagrama de arquitetura:

```mermaid
flowchart TD
    Usuario(["👤 Usuário / Aluno\n[Person]"])

    subgraph Plataforma [Plataforma EAD Gamificada]
        FrontEnd["🖥️ Front-End Web\n[Container: Vue.js]\nInterface consumida pelo aluno"]
        
        subgraph Core [Core de Gamificação - API]
            direction TB
            ModRanking["🏆 Módulo Ranking\n[Component: Java]\n(Candidato à extração futura)"]
            ModCursos["📚 Módulo Cursos\n[Component: Java]"]
            ModConquistas["🎖️ Módulo Conquistas\n[Component: Java]"]
            ModRecompensas["🎟️ Módulo Recompensas\n[Component: Java]"]
        end
        
        DB[("🗄️ Banco de Dados Relacional\n[Container: PostgreSQL]")]
    end

    Usuario -- "Acessa [HTTPS]" --> FrontEnd
    FrontEnd -- "Consome [REST / JSON]" --> Core
    Core -- "Lê / Escreve [JPA / JDBC]" --> DB

    %% Declarando os estilos de forma segura para o GitHub
    classDef cPerson fill:#08427b,stroke:#052e56,color:#fff
    classDef cContainer fill:#438dd5,stroke:#2e6295,color:#fff
    classDef cModule fill:#85bbf0,stroke:#5d82a8,color:#111
    classDef cDatabase fill:#438dd5,stroke:#2e6295,color:#fff

    %% Aplicando as classes aos nós
    class Usuario cPerson
    class FrontEnd cContainer
    class DB cDatabase
    class ModRanking,ModCursos,ModConquistas,ModRecompensas cModule

