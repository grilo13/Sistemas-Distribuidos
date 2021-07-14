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
        print("Capacidade máxima vacinação diária: ", rates_json['vacinasDiarias'], "\n")

def verVacinasDeCentro(idCentro):
    url = 'http://localhost:8080/centro/'
    final_url = url + str(idCentro)
    print(final_url)

    r = requests.get(final_url)

    rates_json = json.loads(r.text)
    print("ID do Centro -> ", rates_json['idCentro'], " Nome Do Centro -> ", rates_json['nomeCentro'])
    print("Vacinas Restantes: ", rates_json['vacinasDiarias'])



#utente regista-se para vacinação
def registarParaVacinacao(nome, email, dataPreferida, idCentro):

    data = {'nomeUtente': nome, 'email': email, 'dataPreferida': dataPreferida}

    payload = json.dumps(data)

    #regista o cliente
    r = requests.post('http://localhost:8080/registaUtente', data=payload, headers = headers)

    valores = json.loads(r.text)

    data2 = {'idUtente': valores['idUtente'], 'idCentro': idCentro, 'dataVacina': dataPreferida}

    payload2= json.dumps(data2)

    r2 = requests.post('http://localhost:8080/utente/filaVacinacao', data=payload2, headers = headers)

    #insere na fila de vacinação
    valores = json.loads(r.text)

    valores2 = json.loads(r2.text)
    
    print("Registo efetuado para vacinação no centro", valores2['idCentro'], 'no dia', valores2['dataVacina'])
    print("Guarde o seguinte código para efeitos de registo de vacinação: ", valores2['idUtente'])
    print("O registo para vacinação está sujeito a reagendamento, dependendo das vacinas disponiveis no centro.")



def vacinasNoDia(idCentro, dataVacina):
    url = 'http://localhost:8080/utente/filaVacinacao/'
    final_url = url + idCentro + '/' + dataVacina

    r = requests.get(final_url)

    aux = json.loads(r.text)['key']  #vacinas existentes para a data dada
    aux2 = json.loads(r.text)['key2'] #capacidade de vacinas diaria desse centro

    if int(aux) >= int(aux2) :
        return 1
    return 0


def notificaçõesUtente(idUtente, emailUtente):
    url = 'http://localhost:8080/utente/notificacao/'
    final_url = url + idUtente + "/" + emailUtente

    r = requests.get(final_url)

    aux = json.loads(r.text)['key']  #notificacao obtida (ou tem ou não tem)
    print(aux,"\n")

    return aux

def removeNotificacao(idUtente):
    url = 'http://localhost:8080/utente/notificacao/remove/'
    final_url = url + idUtente

    r = requests.delete(final_url)

    print(json.loads(r.text)['key'],".\n")


def reagendarVacina(idUtente, novaData, idCentro):
    url = 'http://localhost:8080/utenteRegistado/'
    final_url = url + idUtente 

    r = requests.get(final_url)

    data = json.loads(r.text)

    data['dataPreferida'] = novaData  #altera a data anterior para a nova pedida

    payload= json.dumps(data)

    url2 = 'http://localhost:8080/utente/alterarData/'
    final_url2 = url2 + idUtente 

    r2 = requests.put(final_url2, data=payload, headers = headers)

    data3 = {'idUtente': idUtente, 'idCentro': idCentro, 'dataVacina': novaData}

    payload3 = json.dumps(data3)

    r3 = requests.post('http://localhost:8080/utente/filaVacinacao', data=payload3, headers = headers)

    print(json.loads(r.text)['nomeUtente'], "alterou a sua data de vacinação para o dia", json.loads(r3.text)['dataVacina'], " no centro", idCentro,".")

if __name__ == "__main__":
    auxiliar = 1

    while auxiliar == 1:
        print("-------------   MENU   -------------")
        print("Que operação deseja realizar?\n")
        print("1: Consultar centros de vacinação")
        print("2: Registar para vacinação")
        print("3: Ver notificações")
        print("4: Reagendar vacina")
        print("Outros: sair")

        s = input()
        aux = int(s)

        if(aux == 1) :
            print("\nCentros Disponiveis para Vacinação\n")
            centrosDisponiveis()
            continue

        elif(aux == 2):
            print("--->  Registo de Vacinação  <--")
            print("Insira o seu nome, email, a sua data de escolha, e o centro preferido para vacinacao: ")
            nome = input()
            email = input()
            dataPreferida = input()

            i = 0

            while(i == 0):
                idCentro = input()

                avanca = vacinasNoDia(idCentro, dataPreferida)

                if (avanca == 1):  #se o centro escolhido na data referida estiver cheio
                    print("Centro cheio na data pretendida. Insira novamente outro centro: ")
                else :
                    registarParaVacinacao(nome, email, dataPreferida, idCentro)
                    continue

        elif(aux == 3):
            print("--->  Notificações  <---")
            print("Insira o seu id e o seu email")
            idUtente = input()
            emailUtente = input()
            print("\n")
            valor = notificaçõesUtente(idUtente, emailUtente)

            if valor == "Utente não tem notificações no momento.":
                continue
            
            elif valor == "Os valores dados nao corresnpondem a um utente existente.":
                continue
            else:
                print("Para marcar como lida a notificação insira 1 (e 0 se não quiser)")
                ler_notificacao = input()

                if int(ler_notificacao) == 1:
                    removeNotificacao(idUtente)
                    continue

                else:
                    continue

        elif(aux == 4):
            print("--->  Reagendamento da Vacina  <---")
            print("Indique o seu id de utente anterior (que recebeu ao fazer pela primeira vez o registo), a nova data que quer agendar seguida do centro (que pode ser o mesmo)")
            idUtente = input()
            novaData = input()

            i = 0

            while(i == 0):
                idCentro = input()

                avanca = vacinasNoDia(idCentro, novaData)

                if (avanca == 1):  #se o centro escolhido na data referida estiver cheio
                    print("Centro cheio na data pretendida. Insira novamente outro centro: ")
                else :
                    reagendarVacina(idUtente, novaData, idCentro)
                    i = 1
        
        else:
            print("\nSaindo da aplicação...")
        auxiliar = 2