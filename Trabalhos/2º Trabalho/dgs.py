import requests
import json

headers={
    'Content-type':'application/json', 
}

def atribuirDoses(idCentro, dataVacina, dosesExistentes):

    data = {
            "vacDpkey": {
                "idCentro": idCentro, 
                "dataVacina": dataVacina},
            "dosesVacina":dosesExistentes,
            }


    payload = json.dumps(data)

    #regista o cliente
    r = requests.post('http://localhost:8080/dgs/doses', data=payload, headers = headers)

    valores = json.loads(r.text)

    valores2 = valores['vacDpkey']


    print("Inseridas", valores['dosesVacina'], "vacinas para a data" , valores2['dataVacina'], "do centro" , valores2['idCentro'],".\n")

def estatisticasVacinacao():
    url = 'http://localhost:8080/dgs/estatisticaVacinados'
    r = requests.get(url)

    tamanho_query = json.loads(r.text)["0"]

    for x in range(1,int(tamanho_query)+1):
        indice = str(x)
        aux = json.loads(r.text)[indice]

        string_list = aux.split(",")
        print("Dia:", string_list[1], "Vacina:", string_list[0], "Doses:", string_list[2])

    print("\n")

if __name__ == "__main__":
    auxiliar = 1

    while auxiliar == 1:
        print("Que operação deseja realizar?\n")
        print("1: Enviar número de doses para centros")
        print("2: Estatisticas sobre Vacinação")
        print("Outros: Sair")

        s = input()
        aux = int(s)

        if(aux == 1):
            print("\n--->  Serviço de Disponibilização de Doses  <---\n")
            print("Insira o número do centro, a data e as doses disponibilizadas")
            idCentro = input()
            data = input()
            doses_disponibilizadas = input()
            atribuirDoses(idCentro, data, doses_disponibilizadas)
            continue

        elif(aux == 2):
            print("\n--->  Estatisticas de Vacinação  <---\n")
            estatisticasVacinacao()
            continue

        else:
            print("\nSaindo da aplicação...")
        auxiliar = 2