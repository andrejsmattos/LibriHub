package br.com.andrejsmattos.librihub.repository;

import br.com.andrejsmattos.librihub.model.Idioma;
import br.com.andrejsmattos.librihub.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTitulo(String titulo);

    List<Livro> findByIdioma(Idioma idioma);
}
