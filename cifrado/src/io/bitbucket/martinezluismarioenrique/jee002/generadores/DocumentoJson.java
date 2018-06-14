package io.bitbucket.martinezluismarioenrique.jee002.generadores;
import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

public final class DocumentoJson {
	public static String deLibros(MensajeCifrado mensaje, int opcion) {
		StringBuffer documento = new StringBuffer();
		
		documento.append("{");
			documento.append(mensaje.toJson(opcion));
		documento.append("}");
		
		return documento.toString();
	}
}