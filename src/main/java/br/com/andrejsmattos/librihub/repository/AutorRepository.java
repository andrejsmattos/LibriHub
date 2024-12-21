package br.com.andrejsmattos.librihub.repository;

import br.com.andrejsmattos.librihub.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
