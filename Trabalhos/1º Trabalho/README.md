# 1º Trabalho SD

- Autores: Pedro Grilo e Diogo Castanho

## Para compilar
- Na pasta base do ficheiro
  - 1. rmiregistry -J-classpath -Jbin 9002
  - 2. java -classpath bin sd.rmi.PalavrasServer 9002
  - 3. java -classpath bin sd.rmi.PalavrasClient localhost 9002 ola
