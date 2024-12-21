package br.com.andrejsmattos.librihub.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros;

    public Autor() {
    }

    public Autor(String nome, Integer anoNascimento, Integer anoFalecimento, List<Livro> livros) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
        this.livros = livros;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        StringBuilder autor = new StringBuilder();
        autor.append("Autor: ").append(nome)
                .append("\nData de nascimento: ").append(anoNascimento)
                .append("\nData de falecimento: ").append(anoFalecimento)
                .append("\nLivros: ");
        if (livros != null && !livros.isEmpty()) {
            for (Livro livro: livros) {
                autor.append("[").append(livro.getTitulo()).append("] ");
            }
        } else {
            autor.append("[]");
        }
        return autor.toString();
    }
}