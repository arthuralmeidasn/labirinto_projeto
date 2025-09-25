# Projeto de Estrutura de Dados: Labirinto LIFO – Chaves & Portas

## Relatório Dissertativo

O projeto Labirinto foi feito em Java com a ideia de criar um jogo onde o jogador percorre um mapa em forma de labirinto, podendo encontrar paredes, portas, chaves e tesouros. Além de jogar, o sistema guarda um ranking com a pontuação dos participantes.

O código está dividido em pacotes, o que ajuda a manter a organização. Existe a parte principal que roda o jogo e outras partes que cuidam do mapa, da leitura de arquivos, das estruturas de dados e das regras. Essa divisão deixa o projeto mais limpo e facilita caso alguém precise alterar ou entender cada função.

O mapa do jogo é lido de um arquivo de texto, onde cada caractere representa um tipo de espaço, como parede, caminho livre, porta, chave ou posição inicial. Essa escolha é útil porque qualquer pessoa pode criar um novo mapa apenas editando o arquivo, sem mexer no código. Isso torna o projeto flexível e mais fácil de expandir.

A lógica de movimentação é baseada em busca de caminhos. O jogo utiliza uma pilha, que serve para guardar os passos já feitos e possibilita voltar atrás quando o jogador encontra um beco sem saída. Esse processo de ir avançando e retrocedendo é o que permite explorar todo o labirinto.

O ponto que dá mais dificuldade é a presença das portas e chaves. Para passar por uma porta, o jogador precisa ter a chave correta. Assim, não basta só chegar a um local, é preciso também ter coletado os itens necessários. Isso faz com que a quantidade de possibilidades aumente bastante, pois a mesma posição pode ter resultados diferentes dependendo de quais chaves já foram obtidas.

Sobre a eficiência, a leitura do mapa depende apenas do número de posições, então cresce de forma linear. A exploração do labirinto, sem considerar as chaves, também é linear, mas quando se levam em conta todas as combinações possíveis de chaves e portas, o custo pode aumentar bastante e até se aproximar de algo exponencial no pior caso. Apesar disso, em mapas normais, o jogo funciona de forma aceitável.

O ranking é mantido em um arquivo csv. Cada vez que um jogador termina, sua pontuação é registrada nesse arquivo. Para manter a lista organizada, as pontuações são ordenadas. Como o Java já tem funções prontas para isso, o desempenho é bom mesmo com vários jogadores. A gravação dos dados também é simples, basta escrever cada linha no arquivo.

No geral, o projeto apresenta soluções práticas e ao mesmo tempo mostra conceitos de programação de forma aplicada. A leitura de mapas externos dá liberdade, o uso da pilha ajuda no entendimento do funcionamento do jogo e o ranking em csv é direto e funcional. Uma possível melhoria seria evitar explorar estados repetidos no labirinto, o que deixaria a busca mais rápida. Também seria interessante ter uma interface mais amigável, mas para fins de estudo o que existe já cumpre o que foi proposto.

Em resumo, o Labirinto é um projeto que une teoria e prática. Ele mostra na prática o uso de estruturas de dados e algoritmos, mas em um contexto de jogo que torna o aprendizado mais interessante. Mesmo com limitações, é um trabalho válido e bem construído para fins acadêmicos.

## Pseudocódigos

**Leitura do mapa**
```bash
função lerMapa(arquivo)
    abrir arquivo
    criar lista de linhas
    para cada linha do arquivo
        guardar na lista
    criar matriz de células com altura = linhas e largura = comprimento da primeira linha
    para cada posição da matriz
        se caractere for '#', marcar como parede
        se for '.', marcar como caminho
        se for 'P', marcar como porta
        se for 'C', marcar como chave
        se for 'S', marcar como início
        se for 'T', marcar como tesouro
    retornar matriz do mapa
```

**Busca no labirinto**
```bash
função explorar(posição, mapa, chaves)
    se posição for objetivo
        retornar verdadeiro
    marcar posição como visitada
    para cada direção (cima, baixo, esquerda, direita)
        novaPos = mover(posição, direção)
        se novaPos for válida e não for parede e não foi visitada
            se for porta e não temos chave
                continuar
            se for chave
                adicionar chave
            se explorar(novaPos, mapa, chaves)
                retornar verdadeiro
            se foi usada chave nessa tentativa
                remover chave (voltar atrás)
    desmarcar posição
    retornar falso
```

**Ordenação do ranking**
```bash
função ordenarRanking(listaJogadores)
    usar algoritmo de ordenação (sort) pela pontuação
    retornar lista ordenada
```

## Decisões de Projeto

- O mapa é lido de arquivo de texto para facilitar mudanças sem alterar código.
- Foi escolhida a pilha para controlar o avanço e o retrocesso no labirinto.
- O sistema de portas e chaves aumenta a dificuldade do jogo.
- O ranking é salvo em um arquivo csv por ser simples de manipular.
- O projeto foi dividido em pacotes para manter a organização do código.

## Análise de Complexidade (Big O)

- Leitura do mapa: O(N × M), sendo N e M as dimensões do mapa.
- Exploração do labirinto sem chaves: O(V), onde V é o número de posições.
- Exploração do labirinto com chaves: O(V × 2^K), no pior caso, sendo K o número de chaves.
- Ordenação do ranking: O(N log N), onde N é a quantidade de jogadores.
- Gravação do ranking: O(N).


# Como Compilar e Executar

Siga os passos abaixo para compilar e rodar o projeto.

### Pré-requisitos

* Java Development Kit (JDK) versão 11 ou superior instalado.

### 1. Compilação

Com o terminal aberto na pasta raiz do projeto, execute o seguinte comando. Ele irá compilar todos os arquivos-fonte da pasta `src/` e colocar os arquivos `.class` resultantes na pasta `bin/`.

```bash
javac -d bin/ src/app/*.java src/core/*.java src/ds/*.java src/io/*.java src/model/*.java src/util/*.java
```

### 2. Execução

Após compilar, execute o jogo usando o comando abaixo. É crucial usar a flag `-cp bin` para indicar ao Java onde encontrar as classes compiladas.

**Sintaxe:**
```bash
java -cp bin app.Game --map=<caminho_do_mapa> --player=<nome_do_jogador> --seed=<numero>
```

**Exemplo Prático:**
```bash
java -cp bin app.Game --map=mapas/MAPA_TESTE.txt --player="Manuel_Gomes" --seed=123
```

**Argumentos da Linha de Comando:**
* `--map` (Obrigatório): Define o caminho do arquivo de mapa que será carregado.
* `--player` (Opcional): Define o nome do jogador que será salvo no ranking.
* `--seed` (Opcional): Um número que controla a geração dos valores dos tesouros.

## Autores

* Arthur De Almeida
* Ali Ahmad