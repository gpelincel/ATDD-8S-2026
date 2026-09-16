Aqui está o `README.md` completo e atualizado com as informações dos integrantes, a User Story escolhida e os cenários devidamente atribuídos, omitindo o erro do cenário 5:

---

# Educação Continuada Gamificada

## Descrição do Estudo de Caso

Uma determinada plataforma vende cursos online e EAD no modelo de assinaturas. O
aluno paga um valor mensal e tem acesso a um conjunto de cursos para assinatura
básica. A cada curso terminado e com média acima de 7,0, o aluno tem direito a
realização de mais 3 cursos. O aluno que escrever mais tópicos no fórum e ajudar outros
participantes com seus comentários, ganha um curso no final do mês. Quando o aluno
conquistar 12 cursos, seu plano de assinatura passa a ser “Premium” e ele passa a
receber voucher para participar de projetos reais, durante os cursos, e receber 3 moedas,
que podem ser convertidas em conhecimento (novos cursos), acumular ou receber por
criptomoeda.

## User Stories (US)

* **US1 (por Pedro Henrique Santa):** COMO Aluno do nível básico, QUERO que minha assinatura seja alterada para “Premium” automaticamente quando conquistar os 12 cursos PARA ter acesso ao recebimento dos vouchers e moedas.
* **US2 (por Gabriel Pelincel Ramalho):** COMO Aluno do nível Premium, QUERO usar minhas moedas PARA comprar novos cursos.

### US Escolhida

A User Story escolhida para o detalhamento BDD e TDD foi a **US1** (elaborada por Pedro Henrique Santa):

> *COMO Aluno do nível básico, QUERO que minha assinatura seja alterada para “Premium” automaticamente quando conquistar os 12 cursos PARA ter acesso ao recebimento dos vouchers e moedas.*

---

## BDD - Scenarios (Critérios de Aceitação)

### Cenário 1: Tornar-se Premium ao completar cursos

**Redigido por:** Pedro Henrique Santa

* **Dado** que o aluno possui 11 cursos conquistados
* **E** possui assinatura básica
* **Quando** conquistar mais 1 curso
* **Então** deverá possuir 12 cursos
* **E** sua assinatura deverá ser alterada
* **E** deve possuir acesso ao recebimento dos voucher

### Cenário 2: Receber moedas como Premium

**Redigido por:** Pedro Henrique Santa

* **Dado** que o aluno possui 11 cursos conquistados
* **E** possui assinatura básica
* **Quando** conquistar mais 1 curso
* **Então** terá sua assinatura alterada
* **E** receber 3 moedas

### Cenário 3: Receber voucher e acesso a projetos reais

**Redigido por:** Gabriel Pelincel Ramalho

* **Dado** que o aluno possui 11 cursos conquistados
* **E** possui assinatura básica
* **Quando** conquistar mais 1 curso
* **Então** terá sua assinatura alterada
* **E** receber 1 voucher
* **E** ter acesso a projetos reais, durante os cursos

### Cenário 4: Resgatar voucher em projeto real

**Redigido por:** Gabriel Pelincel Ramalho

* **Dado** que o aluno possui 11 cursos conquistados
* **E** possui assinatura básica
* **Quando** conquistar mais 1 curso
* **Então** terá sua assinatura alterada
* **E** receber 1 voucher
* **E** resgatar 1 voucher em 1 projeto real