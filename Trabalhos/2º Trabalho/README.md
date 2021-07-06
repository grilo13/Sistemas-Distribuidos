## Testar métodos POST via terminal
### Insere um novo utente para vacinação
curl -d '{"nomeUtente":"Joao Santos", "códigoRegisto":"3"}' -H 'Content-Type: application/json' http://localhost:8080/utente
