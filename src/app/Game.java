package app;

import core.MapData;
import ds.SinglyLinkedList;
import ds.Stack;
import io.MapLoader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import model.ScoreEntry;
import model.ScoreBoard;
import util.Sorts;

public class Game {

    public static void main(String[] args) {
        int playerRow = -1;
        int playerCol = -1;
        int score = 0;
        boolean gameIsRunning = true;
        long seed = 42;

        try (Scanner scannerNome = new Scanner(System.in)) {

            System.out.print("Bem-vindo ao Labirinto LIFO! Digite seu nome: ");
            String nomeDoJogador = scannerNome.nextLine();

            try {
                MapData dadosDoMapa = MapLoader.load("mapa_teste.txt");

                for (int i = 0; i < dadosDoMapa.rows(); i++) {
                    for (int j = 0; j < dadosDoMapa.cols(); j++) {
                        if (dadosDoMapa.map()[i][j] == 'S') {
                            playerRow = i;
                            playerCol = j;
                            break;
                        }
                    }
                    if (playerRow != -1) {
                        break;
                    }
                }

                if (playerRow == -1) {
                    System.out.println("Erro: Posição inicial 'S' não encontrada no mapa!");
                    return;
                }

                Scanner scanner = new Scanner(System.in);
                Stack<Character> inventario = new Stack<>(dadosDoMapa.stackCapacity());
                SinglyLinkedList<String> trapLog = new SinglyLinkedList<>();

                while (gameIsRunning) {
                    clearConsole();
                    renderMap(dadosDoMapa.map(), dadosDoMapa.rows(), dadosDoMapa.cols());
                    renderStatus(score, inventario); // Mostra o status do jogador

                    System.out.print("Digite seu movimento (W/A/S/D) ou Q para sair: ");
                    String input = scanner.nextLine().toUpperCase();
                    if (input.isEmpty())
                        continue;

                    char command = input.charAt(0);

                    if (command == 'Q') {
                        gameIsRunning = false;
                        continue;
                    }

                    int nextRow = playerRow;
                    int nextCol = playerCol;

                    switch (command) {
                        case 'W':nextRow--;break;
                        case 'A':nextCol--;break;
                        case 'S':nextRow++;break;
                        case 'D':nextCol++;break;
                        default:continue;
                    }

                    if (nextRow >= 0 && nextRow < dadosDoMapa.rows() &&
                            nextCol >= 0 && nextCol < dadosDoMapa.cols()) {

                        char nextTile = dadosDoMapa.map()[nextRow][nextCol];

                        boolean canMove = false;

                        if (nextTile == '#') {
                            canMove = false;
                        } else if (Character.isUpperCase(nextTile) && nextTile != 'T' && nextTile != 'E') {
                            if (!inventario.isEmpty() && inventario.peek() == Character.toLowerCase(nextTile)) {
                                inventario.pop();
                                score += 15;
                                canMove = true;
                            } else {
                                canMove = false;
                            }
                        } else {
                            canMove = true;
                        }

                        if (canMove) {
                            score--;
                            dadosDoMapa.map()[playerRow][playerCol] = '.';

                            playerRow = nextRow;
                            playerCol = nextCol;
                            char currentTile = dadosDoMapa.map()[playerRow][playerCol];

                            if (Character.isLowerCase(currentTile)) {
                                inventario.push(currentTile);
                            } else if (currentTile == '$') {
                                long treasureSeed = seed + playerRow * 1000 + playerCol; 
                                Random treasureRng = new Random(treasureSeed);
                                int valorTesouro = treasureRng.nextInt(41) + 10;
                                score += valorTesouro;
                                System.out.println("DEBUG: Tesouro coletado! Valor: +" + valorTesouro);
                            } else if (currentTile == 'T') {
                                score -= 20;
                                String logMessage = "Armadilha encontrada na posição (" + playerRow + ", " + playerCol + ")";
                                trapLog.add(logMessage);
                            } else if (currentTile == 'E') {
                                score += 100;
                                gameIsRunning = false;
                            }

                            if (gameIsRunning) {
                                dadosDoMapa.map()[playerRow][playerCol] = 'S';
                            }
                        }
                    }
                }

                ScoreEntry pontuacaoFinal = new ScoreEntry(nomeDoJogador, score);
                System.out.println("\nParabéns, " + pontuacaoFinal.getNome() + "! Sua pontuação final foi: "
                        + pontuacaoFinal.getPontos());

                System.out.println("Salvando sua pontuação no ranking...");
                ScoreBoard.salvarPontuacao(pontuacaoFinal);

                ScoreEntry[] ranking = ScoreBoard.carregarPontuacoes();

                if (ranking.length > 1) {
                    Sorts.ordenarRapido(ranking, 0, ranking.length - 1);
                }

                System.out.println("\n--- TOP 10 RANKING ---");
                int limite = Math.min(ranking.length, 10);
                for (int i = 0; i < limite; i++) {
                    System.out.println(
                            (i + 1) + ". " + ranking[i].getNome() + " - " + ranking[i].getPontos() + " pontos");
                }
                System.out.println("----------------------");

                System.out.println("\n--- Log de Armadilhas Encontradas ---");
                trapLog.printList(); 
                System.out.println("-------------------------------------");

                while (true) {
                    System.out.print("\nDigite um nome para buscar no ranking (ou 'sair' para terminar): ");
                    String nomeBusca = scanner.nextLine();
                    if (nomeBusca.equalsIgnoreCase("sair")) {
                        break;
                    }

                    boolean encontrado = false;
                    for (ScoreEntry entry : ranking) {
                        if (entry.getNome().equalsIgnoreCase(nomeBusca)) {
                            System.out.println(
                                    ">> Resultado: " + entry.getNome() + " - " + entry.getPontos() + " pontos.");
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println(">> Jogador '" + nomeBusca + "' não encontrado no ranking.");
                    }
                }

                scanner.close();
                scannerNome.close();
                System.out.println("\nObrigado por jogar!");

            } catch (FileNotFoundException e) {
                System.err.println("ERRO CRÍTICO: Arquivo do mapa não foi encontrado!");
            }
        }
    }

    public static void renderMap(char[][] map, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void renderStatus(int score, Stack<Character> inventario) {
        System.out.println("--------------------------------------------------");

        System.out.print("Pontos: " + score);

        System.out.println(" | Pilha de chaves: " + (inventario.isEmpty() ? "[]" : "[" + inventario.peek() + "]"));

        System.out.println("--------------------------------------------------");
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}