package model;

public class ScoreEntry {
    private String nome;
    private int pontos;

    public ScoreEntry(String nome, int pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    @Override
    public String toString() {
        return nome + "," + pontos;
    }
}
