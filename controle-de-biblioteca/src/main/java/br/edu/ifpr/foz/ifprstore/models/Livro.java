package br.edu.ifpr.foz.ifprstore.models;

import java.time.LocalDate;

public class Livro {
    private int id;
    private String nome;
    private LocalDate data;
    private LivroStatus status;
    private Autor autor;

    public Livro(int id, String nome, LocalDate data, LivroStatus status, Autor autor) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.status = status;
        this.autor = autor;
    }

    public Livro(String nome, LocalDate data, LivroStatus status, Autor autor) {
        this.nome = nome;
        this.data = data;
        this.status = status;
        this.autor = autor;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LivroStatus getStatus() {
        return status;
    }

    public void setStatus(LivroStatus status) {
        this.status = status;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro [id=" + id + ", nome=" + nome + ", data=" + data + ", status=" + status + ", autor=" + autor + "]";
    }
}
