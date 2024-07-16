package com.challenge.challengeliteratura.entidades;

import com.challenge.challengeliteratura.modelos.Autor;
import com.challenge.challengeliteratura.utilidades.CadenasUtilidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Autor")
public class AutorEntidad {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;


    @OneToOne
    @JoinTable(
            name = "Libro",
            joinColumns = @JoinColumn(name = "autor_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private LibroEntidad libros;


    public AutorEntidad() {

    }

    public AutorEntidad(Autor autor) {
        this.nombre = CadenasUtilidad.limitarLongitud(autor.name(), 200);

        if (autor.birthYear() == null) {
			this.fechaNacimiento = 1;
		} else {
			this.fechaNacimiento = autor.birthYear();
		}

        if (autor.deathYear() == null) {
			this.fechaFallecimiento = 2200;
		} else {
			this.fechaFallecimiento = autor.deathYear();
		}
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }


    @Override
    public String toString() {
        return "AutorEntidad [id=" + id + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
                + ", fechaFallecimiento=" + fechaFallecimiento + ", libro="  + "]";
    }

    public LibroEntidad getLibros() {
        return libros;
    }

    public void setLibros(LibroEntidad libros) {
        this.libros = libros;
    }
}
