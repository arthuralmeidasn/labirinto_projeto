package model;

import java.io.*;
import java.util.ArrayList;

public class ScoreBoard {

    private static final String ARQUIVO = "ranking.csv";

    public static void salvarPontuacao(ScoreEntry entrada) {
        try {
            FileWriter fw = new FileWriter(ARQUIVO, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(entrada.toString());

            pw.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar -> " + e.getMessage());
        }
    }

    public static ScoreEntry[] carregarPontuacoes() {
        File f = new File(ARQUIVO);
        if (!f.exists()) {
            return new ScoreEntry[0];
        }

        ArrayList<ScoreEntry> lista = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    String nome = partes[0];
                    int pontos = Integer.parseInt(partes[1]);
                    lista.add(new ScoreEntry(nome, pontos));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar -> " + e.getMessage());
        }

        return lista.toArray(new ScoreEntry[0]);
    }
}
