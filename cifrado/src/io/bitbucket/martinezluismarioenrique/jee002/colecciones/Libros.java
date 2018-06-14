package io.bitbucket.martinezluismarioenrique.jee002.colecciones;

import java.util.ArrayList;
import java.util.List;

import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

public final class Libros {
	
	public static List<MensajeCifrado> aGenerar(int cantidad) {
		List<MensajeCifrado> libros = new ArrayList<>();
		
		if (cantidad <= 0) {
			return libros;
		}
		
		/* Los objetos de la clase Libro no estÃ¡n siendo inicializados */
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		libros.add(new MensajeCifrado());
		
		if (cantidad < libros.size()) {
			libros.subList(cantidad, libros.size()).clear();	
		}
		
		return libros;
	}
}