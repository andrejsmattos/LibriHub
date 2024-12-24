package br.com.andrejsmattos.librihub.repository;

import br.com.andrejsmattos.librihub.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNomeAndAnoNascimentoAndAnoFalecimento(String nome, Integer anoNascimento, Integer anoFalecimento);
}
