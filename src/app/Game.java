package app;

import core.MapData;
import ds.SinglyLinkedList;
import ds.Stack;
import io.MapLoader;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Game {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES (ESTRUTURA SIMPLIFICADA) ---");

        // Teste da Pilha
        System.out.println("\n1. Testando a Pilha (Inventário)");
        Stack<Character> inventario = new Stack<>(5);
        inventario.push('a');
        inventario.push('b');
        System.out.println("Topo do inventário: " + inventario.peek());
        
        // Teste da Lista Encadeada
        System.out.println("\n2. Testando a Lista Encadeada (Log)");
        SinglyLinkedList<String> log = new SinglyLinkedList<>();
        log.add("Evento 1");
        log.printList();
        
        // Teste do MapLoader
        System.out.println("\n3. Testando o MapLoader");
        try {
            // Este caminho continua o mesmo, pois o arquivo está na raiz do projeto.
            MapData dadosDoMapa = MapLoader.load("mapa_teste.txt");
            System.out.println("Mapa carregado com sucesso!");
            System.out.println("Dimensões: " + dadosDoMapa.rows() + "x" + dadosDoMapa.cols());
            System.out.println("Capacidade do inventário: " + dadosDoMapa.stackCapacity());
            System.out.println("Visualização do Mapa:");
            for (int i = 0; i < dadosDoMapa.rows(); i++) {
                System.out.println(Arrays.toString(dadosDoMapa.map()[i]));
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERRO: Arquivo 'mapa_teste.txt' não encontrado na raiz do projeto.");
        }
    }
}