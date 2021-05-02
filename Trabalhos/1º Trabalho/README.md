# 1º Trabalho SD

- Autores: Pedro Grilo e Diogo Castanho

## Para compilar
- Na pasta base do ficheiro
  - 1. rmiregistry -J-classpath -Jbin 9001
  - 2. java -classpath bin:resources/postgresql.jar sd.rmi.OperacoesServer 9001 localhost l43012 l43012 password
  - 3. java -classpath bin sd.rmi.OperacoesClient localhost 9001
 
 ### Base de dados local
 - psql -h localhost -U l43012
 - password: password
 - user: l43012

