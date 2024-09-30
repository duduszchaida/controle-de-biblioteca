package br.edu.ifpr.foz.ifprstore.models;

public enum LivroStatus {
    DISPONIVEL("Disponível"),
    EMPRESTADO("Emprestado"),
    INDISPONIVEL("Indisponível");

    private String descricao;

    LivroStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static LivroStatus fromString(String status) {
        try {
            return LivroStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }
}