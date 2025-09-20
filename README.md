# Projeto de Estrutura de Dados: Labirinto LIFO – Chaves & Portas

## Como Compilar e Executar

### Pré-requisitos

É necessário ter o Java Development Kit (JDK) instalado e configurado no seu sistema (versão 11 ou superior).

### 1. Compilação

Abra um terminal na pasta raiz do projeto e execute o seguinte comando para compilar todo o código-fonte. O comando irá colocar os arquivos `.class` compilados na pasta `bin/`.

```bash
javac -d bin/ src/app/*.java src/core/*.java src/ds/*.java src/io/*.java src/model/*.java src/util/*.java
```

### 2. Execução

Após a compilação, execute o jogo a partir da pasta raiz usando o comando `java`. É necessário usar a flag `-cp bin` para que o Java encontre as classes compiladas.

**Sintaxe:**
```bash
java -cp bin app.Game --map=<caminho_do_mapa> --player=<nome_do_jogador> --seed=<numero>
```

**Exemplo de Uso:**
```bash
java -cp bin app.Game --map=mapas/map1.txt --player="Thiago" --seed=42
```

* [cite_start]`--map` (obrigatório): Caminho para o arquivo de mapa. [cite: 14]
* [cite_start]`--player` (opcional): Nome do jogador para o ranking. [cite: 15]
* [cite_start]`--seed` (opcional): Semente para geração de valores dos tesouros. [cite: 14]

## Autores

* Arthur De Almeiad
* Ali