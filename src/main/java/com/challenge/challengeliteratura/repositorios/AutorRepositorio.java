package com.challenge.challengeliteratura.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.challengeliteratura.entidades.AutorEntidad;

public interface AutorRepositorio extends JpaRepository<AutorEntidad, Long> {

    @Query("SELECT a FROM AutorEntidad a WHERE :anio between a.fechaNacimiento AND a.fechaFallecimiento")
    List<AutorEntidad> findForYear(int anio);

    @Query("SELECT a FROM AutorEntidad a WHERE a.nombre LIKE %:nom%")
    List<AutorEntidad> findForAutorName(String nom);
}
