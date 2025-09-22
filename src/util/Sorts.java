package util;

import model.ScoreEntry;

public class Sorts {

    public static void ordenarInsercao(ScoreEntry[] lista) {
        for (int i = 1; i < lista.length; i++) {
            ScoreEntry atual = lista[i];
            int pos = i - 1;

            while (pos >= 0 && lista[pos].getPontos() < atual.getPontos()) {
                lista[pos + 1] = lista[pos];
                pos--;
            }
            lista[pos + 1] = atual;
        }
    }

    public static void ordenarRapido(ScoreEntry[] v, int ini, int fim) {
        if (ini < fim) {
            int p = separar(v, ini, fim);
            ordenarRapido(v, ini, p - 1);
            ordenarRapido(v, p + 1, fim);
        }
    }

    private static int separar(ScoreEntry[] v, int ini, int fim) {
        int pivo = v[fim].getPontos();
        int i = ini - 1;

        for (int j = ini; j < fim; j++) {
            if (v[j].getPontos() >= pivo) {
                i++;
                ScoreEntry temp = v[i];
                v[i] = v[j];
                v[j] = temp;
            }
        }

        ScoreEntry temp = v[i + 1];
        v[i + 1] = v[fim];
        v[fim] = temp;

        return i + 1;
    }

    public static int procurarPorNome(ScoreEntry[] arr, String nome) {
        int esq = 0;
        int dir = arr.length - 1;

        while (esq <= dir) {
            int meio = (esq + dir) / 2;
            int comp = arr[meio].getNome().compareToIgnoreCase(nome);

            if (comp == 0) {
                return meio;
            } else if (comp < 0) {
                esq = meio + 1;
            } else {
                dir = meio - 1;
            }
        }
        return -1;
    }
}
