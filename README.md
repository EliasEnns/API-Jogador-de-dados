# 🎲 Rolador de dados - API

Este projeto é uma API desenvolvida em Java que permite rola dados, retornando o valor total somado dos dados jogados. Ele utiliza a API do [Rapid API](https://rapidapi.com/foxjordan/api/diceroller) para rolar a quantidade de dados, com a quantidade de faces que precisar.

## 🛠️ Requisitos

- **Java** (versão 8 ou superior)
- **Maven** (para gerenciamento de dependências)
- **Docker** (para execução em container)

## ⚙️ Preparação

Antes de iniciar o uso da API, siga os passos abaixo:

1. **🔑 Obtenha uma chave API**: Crie uma conta gratuita em [Rapid API](https://rapidapi.com/foxjordan/api/diceroller) e obtenha sua chave API.
2. **📁 Configuração do arquivo de segredo**: 
   - Copie o arquivo `Secret.txt` para outro arquivo nomeado `Secret.java`, localizado no diretório:  
     `Jogador_de_Dados\src\main\java\com\Elias\Jogador_de_Dados\Secrets`.
   - Substitua a string `"SUA_CHAVE_API"` pela chave API obtida no passo anterior.
3. **⏳ Limitações**: A API gratuita possui um limite de 1000 requisições por hora.

## 🧑‍💻 Compilando e Executando o Projeto

### 📦 Compilação

Para compilar o código, execute o seguinte comando no diretório raiz do projeto:

```bash
mvn clean package
```
### 🐳 Execução com Docker

Para criar e iniciar o container Docker:

```bash
docker-compose up --build
```

## 🔗 Endpoints da API

**`GET`** /api/sobre

Retorna informações sobre o projeto.

Exemplo de resposta:

```json
{
  "projeto": "Jogador de Dados",
  "estudante": "Elias Enns"
}
```

**`GET`** /api/consulta

Retorna um array com todas os resultados até o momento.

Exemplo de resposta:

```plaintext
[
  "12",
  "12",
  "9",
  "9",
  "8",
  "146",
  "90",
  "119"
]
```

**`POST`** /api/rolar

Rolar uma quantidade `"numDice"` de dados com `"diceSides"` lados

Corpo da requisição (JSON):
```json
{
  "diceSides": "4",
  "numDice": "20"
}
```

Exemplo de resposta:

```plaintext
69
```

## ℹ️ Observações

O limite de requisições por hora depende do plano gratuito da [Rapid API](https://rapidapi.com)

Esta API foi desenvolvida como parte de um projeto acadêmico por Elias Enns.