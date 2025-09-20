package app;

import core.MapData;
import ds.Stack;
import io.MapLoader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        int playerRow = -1;
        int playerCol = -1;
        int score = 0;
        boolean gameIsRunning = true;
        int nextRow = playerRow;
        int nextCol = playerCol;

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

            while (gameIsRunning) {
                clearConsole();
                renderMap(dadosDoMapa.map(), dadosDoMapa.rows(), dadosDoMapa.cols());
                renderStatus(score, inventario);

                System.out.print("Digite seu movimento (W/A/S/D) ou Q para sair: ");
                String input = scanner.nextLine().toUpperCase();
                if (input.isEmpty())
                    continue;

                char command = input.charAt(0);

                if (command == 'Q') {
                    gameIsRunning = false;
                    continue;
                }

                switch (command) {
                    case 'W':
                        nextRow--;
                        break;
                    case 'A':
                        nextCol--;
                        break;
                    case 'S':
                        nextRow++;
                        break;
                    case 'D':
                        nextCol++;
                        break;
                    default:
                        continue;
                }

                if (nextRow >= 0 && nextRow < dadosDoMapa.rows() &&
                        nextCol >= 0 && nextCol < dadosDoMapa.cols()) {

                    char nextTile = dadosDoMapa.map()[nextRow][nextCol];

                    boolean canMove = false;

                    if (nextTile == '#') {
                        canMove = false;
                    } else if (Character.isUpperCase(nextTile)) {
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
                        dadosDoMapa.map()[playerRow][playerCol] = '.';

                        playerRow = nextRow;
                        playerCol = nextCol;

                        char currentTile = dadosDoMapa.map()[playerRow][playerCol];

                        if (Character.isLowerCase(currentTile)) {
                            inventario.push(currentTile);
                        } else if (currentTile == '$') {
                            score += 25;
                        } else if (currentTile == 'T') {
                            score -= 20;
                        } else if (currentTile == 'E') {
                            score += 100;
                            gameIsRunning = false;
                        }

                        dadosDoMapa.map()[playerRow][playerCol] = 'S';
                    }
                }
            }

            scanner.close();
            System.out.println("\nFim de jogo! Obrigado por jogar.");

        } catch (FileNotFoundException e) {
            System.err.println("ERRO CRÍTICO: Arquivo do mapa não foi encontrado!");
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

        System.out.println(" | Pilha de chaves: " + (inventario.isEmpty() ? "[Vazia]" : "[" + inventario.peek() + "]"));

        System.out.println("--------------------------------------------------");
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}