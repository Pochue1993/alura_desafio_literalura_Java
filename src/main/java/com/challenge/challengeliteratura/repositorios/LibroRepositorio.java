package com.challenge.challengeliteratura.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.challengeliteratura.entidades.LibroEntidad;

public interface LibroRepositorio extends JpaRepository<LibroEntidad, Long>{

	@Query("SELECT l FROM LibroEntidad l WHERE l.lenguaje >= :idioma")
    List<LibroEntidad> findForLanguaje(String idioma);

    @Query("SELECT l FROM LibroEntidad l ORDER BY l.descargas DESC LIMIT 10")
    List<LibroEntidad> findForDownloads();
}
