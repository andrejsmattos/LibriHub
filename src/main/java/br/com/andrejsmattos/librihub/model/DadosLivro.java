package br.com.andrejsmattos.librihub.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosLivro {
    @JsonAlias("title")
    private String titulo;

    @JsonAlias("authors")
    private List<Autor> autores;

    @JsonAlias("languages")
    private List<String> idiomas;

    @JsonAlias("download_count")
    private Long numeroDownloads;

    public static class Autor {
        @JsonAlias("name")
        private String nome;

        @JsonAlias("birth_year")
        private Integer anoNascimento;

        @JsonAlias("death_year")
        private Integer anoFalecimento;

        // Getters and setters
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

        @Override
        public String toString() {
            return "Autor: " + nome +
                    ", Ano de Nascimento: " + anoNascimento +
                    ", Ano de Falecimento: " + anoFalecimento;
        }
    }

    // Getters and setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Long getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Long numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "Livros: " + titulo;
    }
}