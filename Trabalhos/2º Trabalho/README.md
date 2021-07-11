## Testar métodos POST via terminal
### Insere um novo utente para vacinação
curl -d '{"nomeUtente":"Joao Santos", "códigoRegisto":"3"}' -H 'Content-Type: application/json' http://localhost:8080/utente

### Insere um novo centro de vacinação 
curl -d '{"nomeCentro":"Centro de Évora"}' -H 'Content-Type: application/json' http://localhost:8080/centro



## Database get first "x" values from table
select id_utente from fila_vacinacao order by id_utente desc limit 10;
