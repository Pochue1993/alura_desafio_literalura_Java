package com.challenge.challengeliteratura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.challenge.challengeliteratura.cliente.ClienteLiteratura;
import com.challenge.challengeliteratura.repositorios.AutorRepositorio;
import com.challenge.challengeliteratura.repositorios.LibroRepositorio;

@SpringBootApplication
public class LiteraturaChallengeOneApplication implements CommandLineRunner{

	@Autowired
	private LibroRepositorio libroRepositorio;
	@Autowired
	private AutorRepositorio autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaChallengeOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ClienteLiteratura client = new ClienteLiteratura(libroRepositorio, autorRepositorio);
		client.menu();
	}

}
