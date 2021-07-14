import requests
import json

headers={
    'Content-type':'application/json', 
}

def centrosDisponiveis():
    r = requests.get('http://localhost:8080/centrosDisponiveis')
    r2 = requests.get('http://localhost:8080/numeroCentros')

    size = int(r2.json())

    for x in range(0,size) :
        rates_json = json.loads(r.text)[x]
        print("ID do Centro -> ", rates_json['idCentro'], " Nome Do Centro -> ", rates_json['nomeCentro'])

#utente regista-se para vacinação
def registarCentroParaVacinação(nomeCentro, vacinasDiarias):

    data = {'nomeCentro': nomeCentro, 'vacinasDiarias': vacinasDiarias}

    payload = json.dumps(data)

    r = requests.post('http://localhost:8080/centro', data=payload, headers = headers)

    print("Adicionado centro", json.loads(r.text)['nomeCentro'], 'de id', json.loads(r.text)['idCentro'], 'com capacidade máxima de vacinacao de',json.loads(r.text)['vacinasDiarias'])

#registar utente como vacinado
#para isso remove da fila onde estava e dos utentes registados
def registarVacinacao(idUtente, tipo_vacina):
    
    #obter id do centro onde o utente vai ser vacinado
    url2 = 'http://localhost:8080/utente/filaVacinacao/centro/'
    final_url2 = url2 + idUtente

    r2 = requests.get(final_url2)

    id_centro = json.loads(r2.text)['IdCentro']  #retorna idCentro para colocar depois
    data_vacina = json.loads(r2.text)['DataVacina']  #retorna a data prevista da vacina
    

    url = 'http://localhost:8080/utente/filaVacinacao/removeFila/'
    final_url = url + idUtente

    r = requests.delete(final_url)

    aux = json.loads(r.text)['key']  #retorna id do utente removido

    #coloca utente na lista de vacinados com os atributos : idcentro, datavacina e tipo de vacina
    data = {'idUtente': idUtente, 'idCentro': id_centro, 'nomeVacina': tipo_vacina, 'dataVacinacao': data_vacina}

    payload = json.dumps(data)

    r = requests.post('http://localhost:8080/utente/listaVacinados', data=payload, headers = headers)

    valores = json.loads(r.text)

    print("\nRegistada vacinação completa do utente", valores['idUtente'], "no dia",valores['dataVacinacao'], "com a vacina", valores['nomeVacina'],"\n")


def dosesDisponiveis(idCentro, dataVacina):
    url = 'http://localhost:8080/dgs/doses/'
    final_url = url + idCentro + "/" + dataVacina

    r = requests.get(final_url)

    if json.loads(r.text)['verificar'] == 'Dosesmal' :  #verifcar se é necessário fazer algum reagendamento
        doses = json.loads(r.text)['Doses']

        utentesAgendados = json.loads(r.text)['Agendados']

        tamanho = json.loads(r.text)['tamanho']

        tamanho_int = int(tamanho) 

        lista_temp = []

        for x in range(0,tamanho_int):
            lista_temp.append(json.loads(r.text)[str(x)])
            print("O utente ", json.loads(r.text)[str(x)], 'que estava no centro', idCentro, 'no dia', dataVacina,'terá que reagendar a vacina.')

        return lista_temp
    
    elif json.loads(r.text)['verificar'] == 'Dosesbem' :
        
        lista = []
        return lista

def removerUtenteFila(lista_utentes):

    tamanho_lista = len(lista_utentes)

    for x in range(0, tamanho_lista):
        url = 'http://localhost:8080/utente/filaVacinacao/removeFila/'
        final_url = url + lista_utentes[x]

        r = requests.delete(final_url)
        aux = json.loads(r.text)['key']
        print(aux)   #depois de remover falta enviar notificacao de necessidade de reagendamento

def notificaCliente(idUtente):

    notificacao = "Terá que reagendar a sua vacinação por motivos de falta de vacinas no dia especifico que escolheu."

    for x in range(0, len(idUtente)):
        data = {'idUtente': idUtente[x], 'notificacao': notificacao}

        payload = json.dumps(data)

        #regista o cliente
        r = requests.post('http://localhost:8080/utente/notificacao', data=payload, headers = headers)

        valores = json.loads(r.text)

        print("Notificacão enviada para o utente.")


if __name__ == "__main__":
    auxiliar = 1

    while auxiliar == 1:
        print("Que operação deseja realizar?\n")
        print("1: Consultar centros de vacinação")
        print("2: Consultar utentes em filas de espera")
        print("3: Registar um novo centro de vacinação")
        print("4: Confirmar ou cancelar agendamentos")
        print("5: Regista vacinação de um utente")
        print("Outros: sair")

        s = input()
        aux = int(s)

        if(aux == 1) :
            print("\nCentros Disponiveis para Vacinação\n")
            centrosDisponiveis()
            continue


        elif(aux == 2):
            print("\nUtentes em fila de espera")

        elif(aux == 3):
            print("Registe um novo centro de vacinação")
            print("Insira o nome do centro e as vacinas diárias que pode administrar")
            nomeCentro = input()
            vacinasDiarias = input()
            registarCentroParaVacinação(nomeCentro, vacinasDiarias)
            continue

        elif(aux == 4):
            print("\--->  Confirmar ou Cancelar Agendamentos")
            print("Insira o centro e a data para saber quantas doses estarão disponiveis")
            idCentro = input()
            data = input()
            lista = dosesDisponiveis(idCentro, data)

            if len(lista) == 0:
                print("Não existem casos para reagendar no momento.\n")
                continue
            else:
                print("No caso de haver utentes por reagendar, deseja fazer essa operação?")
                print("1: Sim   0: Não")
                decisao = input()
                if int(decisao) == 1:
                    removerUtenteFila(lista)
                    notificaCliente(lista)
                    continue
                else:
                    continue


        elif(aux == 5):
            print("Registe a vacinação de um utente: (pelo seu id) e nome da vacina tomada pelo mesmo")
            idUtente = input()
            data_vacina = input()
            registarVacinacao(idUtente, data_vacina)
            continue

        else:
            print("\nSaindo da aplicação...")

        auxiliar = 2