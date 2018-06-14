package io.bitbucket.martinezluismarioenrique.jee002.generadores;

import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

public final class DocumentoCsv {

	public static String deLibros(MensajeCifrado mensaje, int opcion) {
		StringBuffer documento = new StringBuffer("UTF_8");
		if(opcion == 1) {
			documento.append("mensaje,desplazamiento,mensajeCifrado\n");
		}else if(opcion == 2) {
			documento.append("mensaje,clave,mensajeCifrado\n");
		}else if(opcion == 3) {
			documento.append("mensaje,clave,mensajeDescifrado\n");
		}
		
		
			documento.append(mensaje.toCsv());
		
		return documento.toString();
	}
}