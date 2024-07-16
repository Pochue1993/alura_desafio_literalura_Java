CREATE DATABASE literalura;

CREATE TABLE Autor (
    id INT AUTO_INCREMENT,
    nombre TEXT,
    fecha_nacimiento INT,
    fecha_fallecimiento INT,
    PRIMARY KEY (id)
);

CREATE TABLE Libro (
    id INT AUTO_INCREMENT,
    titulo TEXT,
    lenguaje TEXT,
    descargas INT,
    PRIMARY KEY (id)
);