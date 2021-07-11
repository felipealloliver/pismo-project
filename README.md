# pismo-project

Projeto desenvolvido por Felipe Allan com o objetivo de avaliação em processo seletivo.

## Tecnologias Utilizadas

- Java 11
- Framework Springboot 2.5.2
- Docker
- Postgresql

## Api

### Account

**[GET] /api/v1/accounts/{account_id}**

Response:

Campo | Descrição
--- | ---
account_id | Id da conta
document_number | número do documento informado

**[POST] /api/v1/accounts**

Body:

Campo | Descrição
--- | ---
document_number | Número do documento

Response:

Header 'Location' com a url get do registro criado

### Transaction

**[POST] /api/v1/transactions**

Body:

Campo | Descrição | validação
--- | --- | ---
account_id | id da conta | deve conter uma conta existente
operation_type_id | id do tipo de operação | verifique tabela de ids abaixo
amount | valor da transação | deve ser um número positivo diferente de zero

Response: Código 202

Tabela de Tipo de Operação

operation_type_id | Descrição
--- | ---
1 | Compra à vista 
2 | Compra Parcelada 
3 | Saque
4 | Pagamento

## Api Documentada via Swagger

URL: /swagger-ui.html