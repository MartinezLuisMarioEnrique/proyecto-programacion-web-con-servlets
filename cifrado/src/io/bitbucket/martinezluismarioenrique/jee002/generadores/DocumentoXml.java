package io.bitbucket.martinezluismarioenrique.jee002.generadores;
import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

public final class DocumentoXml {
	public static String deLibros(MensajeCifrado mensaje, int opcion) {
		StringBuffer documento = new StringBuffer();
		
		documento.append("<Mensaje>");
			documento.append(mensaje.toXml(opcion));
		documento.append("</Mensaje>");
		
		return documento.toString();
	}
}
