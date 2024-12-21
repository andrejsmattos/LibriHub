package br.com.andrejsmattos.librihub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @OneToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private Idioma idioma;
    private Long numeroDownloads;

    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.getTitulo();

        if (!dadosLivro.getAutores().isEmpty()) {
            Autor autor = new Autor();
            autor.setNome(dadosLivro.getAutores().get(0).getNome());
            autor.setAnoNascimento(dadosLivro.getAutores().get(0).getAnoNascimento());
            autor.setAnoFalecimento(dadosLivro.getAutores().get(0).getAnoFalecimento());
            this.autor = autor;
        } else {
            this.autor = null;
        }
        this.idioma = dadosLivro.getIdiomas().isEmpty()
                        ? null
                        : Idioma.valueOf(dadosLivro.getIdiomas().get(0));
        this.numeroDownloads = dadosLivro.getNumeroDownloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Long numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "Livro: " + titulo +
                "\nAutor: " + autor.getNome() +
                "\nIdioma: " + idioma.toString() +
                "\nNúmero de downloads: " + numeroDownloads;
    }
}
