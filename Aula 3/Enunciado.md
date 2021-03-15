# Atividade 03 - serialização

Serializar: 
Transformar um objeto numa sequência de bytes adequada à transmissão num canal de rede ou à escrita numa Stream.
O marshalling em RMI faz-se com esta técnica.

Em Java, os tipos primitivos são serializáveis, bem como instâncias de classes que implementem a interface java.io.Serializable.

STREAMS

Para Ler/Escrever recorremos às Streams, no pacote java.io :

    genéricas (abstratas): InputStream e OutputStream
    ler e escrever tipos primitivos: DataInputStream e DataOutputStream
    ler e escrever em FICHEIROS: FileInputStream e FileOutputStream
    ler e escrever OBJETOS SERIALIZÁVEIS: ObjectInputStream e ObjectOutputStream
        simplificam o envio de objetos através de uma stream (por exemplo para comunicação na rede)




1. exercício  ( veículos 02 )
 
1.a) Ainda sobre o exercício na atividade anterior (serviço de informação sobre veículos), considere que o registo de informação de veículo passa a incluir também o ano de matrícula, sendo representado através das classe Registo, incluída no código fonte inicial para esta aula.
Obtenha aqui o código inicial (formatos: eclipse, netbeans).

 
1.b) Implemente as classes Java: 

    Pedido (abstrata)
    PedidoDeRegisto
    PedidoDeConsulta


    // # ficheiro Pedido.java:

    package sd;

    public abstract class Pedido {
    }

     

    // # ficheiro PedidoDeConsulta.java:
    package sd;
    public class PedidoDeConsulta extends Pedido {
     
        private String matricula;
     
        public PedidoDeConsulta(String matricula) {
            this.matricula= matricula;
        }
     
        public String getMatricula() {
            return matricula;
        }
    }


    // # ficheiro PedidoDeRegisto.java:
    package sd;
    public class PedidoDeRegisto extends Pedido {
     
        public Registo reg;
     
        public PedidoDeRegisto(Registo r) {
            this.reg= r;
        }
     
        public Registo getRegisto() {
            return reg;
        }
    }


A intenção é usar objetos representativos do pedido, em vez de uma solução de protocolo diretamente programado, com códigos para representar cada operação.
Assim a semântica do pedido está no próprio objeto enviado.



2- Ao compilar diretamente na linha de comandos, precisará de definir com precisão onde estão as dependências de cada classe.
No comando para compilar, deve indicar na classpath qual é a pasta com os recursos necessários à compilação com um comando semelhante a:
EC:
$ javac -d bin -classpath bin src/sd/VeiculosClient.java
NB:
$ javac -d build/classes -classpath build/classes src/sd/Registo.java
(O IDE trata de tudo, mas deve saber como fazer.)


Poderá ter de executar mais que uma classe no mesmo projeto, e passar diferentes argumentos em cada caso...
(se necessário, consulte exemplos da atividade01)





3.a- A aplicação cliente (sd.VeiculosClient), desta vez, forma uma instância de uma das classes e é apenas esse objeto que escreve no socket, com uma Stream apropriada.

3.b) Note que ao tentar escrever um Pedido, ele tem de ser serializável...
Será necessário algum ajuste? (veja a classe Registo)


3.c) No servidor, depois de aceitar uma ligação deve ler do socket de dados um

    Pedido objPedido
     

e:

            if (objPedido==null)
                System.err.println("PEDIDO NULL: esqueceu-se de alguma coisa");
            else if (objPedido instanceof PedidoDeConsulta) {
                // ler o objeto PedidoDeConsulta
                PedidoDeConsulta pc= (PedidoDeConsulta) objPedido;
 
                //  ... atendimento especifico...
                // procurar aquela matricula numa lista
 
            }
            else if (objPedido instanceof PedidoDeRegisto) {
                // ler o objeto PedidoDeRegisto
                // ... idem...
 
 
                // inserir aquele objeto Registo numa estrutura de dados local...
 
 
 
            }
            else
                System.err.println("PROBLEMA");



3.d) Na versão mais simples, o servidor mantém um repositório (para já apenas em memória, numa coleção) de instâncias da classe Registo como base de dados do serviço.
Teste ambas as aplicações, cliente e servidor, com diferentes pedidos sobre várias matrículas.


4- Altere o servidor para guardar os registos de veículos de modo persistente, em ficheiros (no futuro usaremos uma BD).
Junto ao servidor, deve ter uma pasta "registos", dentro da qual, para cada pedido de registo deve ser escrito um ficheiro (com o nome <matrícula>.object).

Para atender um pedido de consulta, deve procurar a existência do ficheiro para a matrícula procurada, dentro da mesma pasta. Se existir, deve ler e devolver à aplicação cliente o objeto do tipo Registo.
Use a stream mais apropriada ao caso. 



