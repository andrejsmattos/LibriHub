package br.com.andrejsmattos.librihub.principal;

import br.com.andrejsmattos.librihub.model.Autor;
import br.com.andrejsmattos.librihub.model.DadosLivro;
import br.com.andrejsmattos.librihub.model.Idioma;
import br.com.andrejsmattos.librihub.model.Livro;
import br.com.andrejsmattos.librihub.repository.AutorRepository;
import br.com.andrejsmattos.librihub.repository.LivroRepository;
import br.com.andrejsmattos.librihub.service.ConsumoApi;
import br.com.andrejsmattos.librihub.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    @Autowired
    private final LivroRepository livroRepository;

    @Autowired
    private final AutorRepository autorRepository;

    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private List<DadosLivro> dadosLivros = new ArrayList<>();
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;

        while(opcao != 0) {
            var menu = """
                    
                    ESCOLHA O NÚMERO DA SUA OPÇÃO
                    *****************************
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosNumAno();
                    break;
                case 5:
                    listarLivrosDeUmIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        var cadastrarNovo = "S";

        while(cadastrarNovo.equalsIgnoreCase("S")) {
            DadosLivro dados = getDadosLivro();

            if (dados == null) {
                System.out.println("Não foi possível cadastrar este livro. Tente novamente.");
                System.out.println("Deseja tentar cadastra outro livro? (S/N)");
                cadastrarNovo = sc.nextLine();
                continue;
            }
            if (livroRepository.findByTitulo(dados.getTitulo()).isPresent()) {
                System.out.println("\nEste título já foi cadastrado no sistema.");
                System.out.println("\nDeseja tentar cadastra outro livro? (S/N)");
                cadastrarNovo = sc.nextLine();

            } else {
                Livro livro = new Livro();

                if (!dados.getAutores().isEmpty()) {
                    Autor autor = new Autor();
                    autor.setNome(dados.getAutores().get(0).getNome());
                    autor.setAnoNascimento(dados.getAutores().get(0).getAnoNascimento());
                    autor.setAnoFalecimento(dados.getAutores().get(0).getAnoFalecimento());
                    autor.setLivros(List.of(livro));
                    autor = autorRepository.save(autor);
                    livro.setAutor(autor);
                } else {
                    livro.setAutor(null);
                }

                livro.setTitulo(dados.getTitulo());
                livro.setIdioma(Idioma.valueOf(dados.getIdiomas().isEmpty() ? "N/A" : dados.getIdiomas().get(0)));
                livro.setNumeroDownloads(dados.getNumeroDownloads());

                livroRepository.save(livro);
                System.out.println("\n" + dados);

                System.out.println("\nDeseja cadastrar outro livro? (S/N)");
                cadastrarNovo = sc.nextLine();
            }
        }
    }

    private DadosLivro getDadosLivro() {
        System.out.println("\nDigite o título do livro para busca: ");
        var nomeLivro = sc.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.toLowerCase().replace(" ", "+"));
        DadosLivro dados = conversor.obterDados(json, DadosLivro.class);
        if (dados == null || dados.getTitulo() == null || dados.getTitulo().isEmpty()) {
            System.out.println("Nenhum livro encontrado com este título: " + nomeLivro + "\n");
            return null;
        }
        return dados;
    }

    private void listarLivros() {
        livros = livroRepository.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(livro -> {
                    System.out.println();
                    System.out.println(livro);
                });
    }

    private void listarAutores() {
        autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(autor -> {
                    System.out.println();
                    System.out.println(autor);
                });
    }

    private void listarAutoresVivosNumAno() {
        System.out.println("Em que ano você deseja saber a listagem de autores vivos? ");
        var anoBusca = sc.nextInt();

        var autoresVivos = autores.stream()
                .filter(autor -> autor.getAnoNascimento() <= anoBusca &&
                        (autor.getAnoFalecimento() == null || autor.getAnoFalecimento() >= anoBusca))
                .sorted(Comparator.comparing(Autor::getNome))
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor da listagem estava vivo em " + anoBusca);
        } else {
            System.out.println("Autores vivos em " + anoBusca + ":");
            autoresVivos.forEach(autorVivo -> {
                System.out.println();
                System.out.println(autorVivo);
            });
        }
    }

    private void listarLivrosDeUmIdioma() {
        System.out.println("""
            Digite o idioma para realizar a busca: 
            es - Espanhol
            en - Inglês
            fr - Francês
            pt - Português""");
        var idiomaBuscado = sc.nextLine();
        Idioma idioma = Idioma.valueOf(idiomaBuscado.toUpperCase());
        var listagemDeLivrosNoIdiomaBuscado = livroRepository.findByIdioma(idioma);

        if (listagemDeLivrosNoIdiomaBuscado == null || listagemDeLivrosNoIdiomaBuscado.isEmpty()) {
            System.out.println("Não há nenhum livro listado neste idioma: " + idiomaBuscado);
        } else {
            listagemDeLivrosNoIdiomaBuscado.stream()
                    .sorted(Comparator.comparing(Livro::getTitulo))
                    .forEach(livro -> {
                        System.out.println();
                        System.out.println(livro);
                    });
        }
    }
}
