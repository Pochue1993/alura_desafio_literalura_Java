package com.challenge.challengeliteratura.cliente;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.challenge.challengeliteratura.entidades.AutorEntidad;
import com.challenge.challengeliteratura.entidades.LibroEntidad;
import com.challenge.challengeliteratura.mapeadores.ConvertirDatos;
import com.challenge.challengeliteratura.modelos.Libro;
import com.challenge.challengeliteratura.modelos.Respuesta;
import com.challenge.challengeliteratura.repositorios.AutorRepositorio;
import com.challenge.challengeliteratura.repositorios.LibroRepositorio;
import com.challenge.challengeliteratura.servicios.ObtenerDatosAPIServicio;

public class ClienteLiteratura {
	private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ObtenerDatosAPIServicio consumoApi = new ObtenerDatosAPIServicio();
    private ConvertirDatos conversor = new ConvertirDatos();

    private LibroRepositorio libroRepositorio;
    private AutorRepositorio autorRepositorio;

    public ClienteLiteratura(LibroRepositorio libroRepositorio, AutorRepositorio autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
					Opciones disponibles:
						1.- Buscar libro por titulo
						2.- Obtener libros registrados
						3.- Obtener autores registrados
						4.- Obtener autores vivos en un determinado año
						5.- Obtener libros por idioma
						6.- Obtener top 10 libros mas descargados
						7.- Obtener autor por nombre
						8.- Estadisticas
						0 - Salir del sistema
						""";
            System.out.println(menu);
            System.out.println("Ingrese el numero de la opción: ");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarLibros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresVivo();
                    break;
                case 5:
                    buscarPorIdiomas();
                    break;
                case 6:
                    buscarPorDescargas();
                    break;
                case 7:
                	buscarAutorPorNombre();
                	break;
                case 8:
                	estadisticas();
                	break;
                case 0:
                    System.out.println("!Nos vemos! !Hasta la próxima!");
                    break;
                default:
                    System.out.println("Opción no valida");
            }
        }

    }

    private void buscarLibros() {

        List<LibroEntidad> libros = libroRepositorio.findAll();

        if (!libros.isEmpty()) {

            for (LibroEntidad libro : libros) {
                System.out.println("\n---------- LIBRO --------\n");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getDescargas());
                System.out.println("\n-------------------------\n");
            }

        } else {
            System.out.println("\n ----- NO SE ENCONTRARON RESULTADOS ---- \n");
        }

    }

    private void buscarAutores() {
        List<AutorEntidad> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (AutorEntidad autor : autores) {
                System.out.println("\n---------- Autor --------\n");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println(" Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("\n-------------------------\n");
            }
        } else {
            System.out.println("\n----- NO SE ENCONTRARON RESULTADOS ---- \n");

        }

    }

    private void buscarAutoresVivo() {
        System.out.println("Escriba el año para en el que vivió: ");
        var anio = teclado.nextInt();
        teclado.nextLine();

        List<AutorEntidad> autores = autorRepositorio.findForYear(anio);

        if (!autores.isEmpty()) {
            for (AutorEntidad autor : autores) {
                System.out.println("\n---------- Autores Vivos -------\n");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println(" Fecha de fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("\n--------------------------------\n");
            }
        } else {
            System.out.println("\n ----- NO SE ENCONTRARON RESULTADOS ---- \n");

        }
    }

    private void buscarPorIdiomas() {

        var menu = """
				Seleccione un Idioma:
					1.- Español
					2.- Ingles
					3.- Frances
					4.- Italiano

					""";
        System.out.println(menu);
        var idioma = teclado.nextInt();

        String seleccion = "";

        if(idioma == 1) {
            seleccion = "es";
        } else if(idioma == 2) {
            seleccion = "en";
        } else if(idioma == 3) {
        	seleccion = "fr";
        }else {
        	seleccion = "it";
        }

        List<LibroEntidad> libros = libroRepositorio.findForLanguaje(seleccion);

        if (!libros.isEmpty()) {

            for (LibroEntidad libro : libros) {
                System.out.println("\n---------- LIBROS POR IDIOMA-------\n");
                System.out.println(" Titulo del libro: " + libro.getTitulo());
                System.out.println(" Nombre del Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Total de Descargas: " + libro.getDescargas());
                System.out.println("\n-----------------------------------\n");
            }
            System.out.println("Total de libros encontrados: " + libros.size() + "\n");

        } else {
            System.out.println("\n ----- NO SE ENCONTRARON RESULTADOS ---- \n");
        }


    }

    private void buscarLibroWeb() {
        Respuesta datos = getDatosSerie();

        if (!datos.results().isEmpty()) {
        	for (Libro element : datos.results()) {
        		LibroEntidad libro = new LibroEntidad(element);
                libro = libroRepositorio.save(libro);
        	}
            System.out.println("Total de libros encontrados: " + datos.results().size());
        }else {
        	System.out.println("No se encontraron coincidencias...\n ");
        }

    }

    private Respuesta getDatosSerie() {
        System.out.println("Ingresa el nombre del libro que deseas buscar: ");
        var titulo = teclado.next();
        teclado.nextLine();
        titulo = titulo.replace(" ", "%20");
        System.out.println("Titlulo : " + titulo);
        System.out.println(URL_BASE + titulo);
        var json = consumoApi.obtenerDatos(URL_BASE + titulo);
        //System.out.println(json);
        Respuesta datos = conversor.obtenerDatos(json, Respuesta.class);
        return datos;
    }

    private void buscarPorDescargas() {

        List<LibroEntidad> libros = libroRepositorio.findForDownloads();

        if (!libros.isEmpty()) {

            for (LibroEntidad libro : libros) {
                System.out.println("\n---------- LIBRO -------\n");
                System.out.println(" Titulo del libro: " + libro.getTitulo());
                System.out.println(" Nombre del Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Total de Descargas: " + libro.getDescargas());
                System.out.println("\n-------------------------\n");
            }
            System.out.println("Total de libros encontrados: " + libros.size());

        } else {
            System.out.println("\n ----- NO SE ENCONTRARON RESULTADOS ---- \n");
        }
    }

    private void buscarAutorPorNombre() {

        System.out.println("Introduzca un nombre para hacer la busqueda: ");
        var nombre = teclado.next();
        teclado.nextLine();

        System.out.println("Buscando datos... ");
        List<AutorEntidad> autores = autorRepositorio.findForAutorName(nombre);

        if (!autores.isEmpty()) {
            for (AutorEntidad autor : autores) {
                System.out.println("\n---------- AUTOR POR NOMBRE -------\n");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println(" Fecha de fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("\n-----------------------------------\n");
            }
            System.out.println("Total de autores encontrados: " + autores.size() + "\n");

        } else {
            System.out.println("\n ----- NO SE ENCONTRARON RESULTADOS ---- \n");
        }


    }

    private void estadisticas() {
    	List<LibroEntidad> libros = libroRepositorio.findAll();
    	int esp = libros.stream().filter(c -> c.getLenguaje().equals("es")).collect(Collectors.toList()).size();
    	int ing = libros.stream().filter(c -> c.getLenguaje().equals("en")).collect(Collectors.toList()).size();
    	int fra = libros.stream().filter(c -> c.getLenguaje().equals("fr")).collect(Collectors.toList()).size();
    	int ita = libros.stream().filter(c -> c.getLenguaje().equals("it")).collect(Collectors.toList()).size();
    	int otros = libros.stream()
    			.filter(c -> (!c.getLenguaje().equals("es") && !c.getLenguaje().equals("en") &&
    					!c.getLenguaje().equals("fr") && !c.getLenguaje().equals("it"))
    			).collect(Collectors.toList()).size();

    	List<AutorEntidad> autores = autorRepositorio.findAll();
    	int fallecidos = autores.stream().filter(c -> c.getFechaFallecimiento() < 2200).collect(Collectors.toList()).size();
    	int vivos = autores.stream().filter(c -> c.getFechaFallecimiento() >= 2200).collect(Collectors.toList()).size();

    	System.out.println("\n---------- ESTADISTICAS -------\n");
    	System.out.println("Total de libros registrados: " + libros.size());
    	System.out.println("Libros en español: " + esp);
    	System.out.println("Libros en ingles: " + ing);
    	System.out.println("Libros en frances: " + fra);
    	System.out.println("Libros en italiano: " + ita);
    	System.out.println("Libros en otros idiomas: " + otros + "\n");
    	System.out.println("Total de autores registrados: " + autores.size());
    	System.out.println("Autores que ya fallecieron: " + fallecidos);
    	System.out.println("Autores que aún están vivos: " + vivos + "\n");
    }
}
