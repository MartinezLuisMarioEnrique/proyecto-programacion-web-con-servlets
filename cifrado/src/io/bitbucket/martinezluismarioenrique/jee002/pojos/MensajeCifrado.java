package io.bitbucket.martinezluismarioenrique.jee002.pojos;

public class MensajeCifrado {
	private String mensaje;
	private String desplazamiento;	
	private String mensajeCifrado;
	
	public MensajeCifrado() {
	}

	public MensajeCifrado(
			String mensaje,
			String desplazamiento,
			String mensajeCifrado) {
		
		this.mensaje = mensaje;
		this.desplazamiento = desplazamiento;
		this.mensajeCifrado = mensajeCifrado;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDesplazamiento() {
		return desplazamiento;
	}

	public void setDesplazamiento(String desplazamiento) {
		this.desplazamiento = desplazamiento;
	}

	public String getMensajeCifrado() {
		return mensajeCifrado;
	}

	public void setMensajeCifrado(String mensajeCifrado) {
		this.mensajeCifrado = mensajeCifrado;
	}
	
	public String toCsv() {
		StringBuffer representacion = new StringBuffer();
		representacion.append(mensaje + ",");
		representacion.append(desplazamiento + ",");
		representacion.append(mensajeCifrado);		
		return representacion.toString();
	}
	
	public String toXml(int opcion) {
		StringBuffer representacion = new StringBuffer();
		if(opcion == 1) {
			representacion.append("<Mensaje desplazamiento=\"" + desplazamiento + "\">");
			representacion.append("<mensaje>" + mensaje + "</mensaje>");
			representacion.append("<mensajeCifrado>" + mensajeCifrado + "</mensajeCifrado>");
			representacion.append("</Mensaje>");
		}else if(opcion == 2) {
			representacion.append("<Mensaje clave=\"" + desplazamiento + "\">");
			representacion.append("<mensaje>" + mensaje + "</mensaje>");
			representacion.append("<mensajeCifrado>" + mensajeCifrado + "</mensajeCifrado>");
			representacion.append("</Mensaje>");
		}else if(opcion == 3) {
			representacion.append("<Mensaje clave=\"" + desplazamiento + "\">");
			representacion.append("<mensaje>" + mensaje + "</mensaje>");
			representacion.append("<mensajeDescifrado>" + mensajeCifrado + "</mensajeDescifrado>");
			representacion.append("</Mensaje>");
		}
		
		return representacion.toString();
	}
	
	public String toJson(int opcion) {
		StringBuffer representacion = new StringBuffer();
		if(opcion == 1) {
			representacion.append("\"mensaje\": \"" + mensaje + "\",");
			representacion.append("\"desplazamiento\": " + desplazamiento + ",");
			representacion.append("\"mensajeCifrado\": \"" + mensajeCifrado+"\"");
			return representacion.toString();
			
						
		}else if(opcion == 2) {
			representacion.append("\"{mensaje\": " + mensaje + "},");
			representacion.append("\"{clave\": " + desplazamiento + "},");
			representacion.append("\"{mensajeCifrado\": " + mensajeCifrado);
			representacion.append("}");
		}else if(opcion == 3) {
			representacion.append("\"{mensaje\": " + mensaje + "},");
			representacion.append("\"{clave\": " + desplazamiento + "},");
			representacion.append("\"{mensajeDescifrado\": " + mensajeCifrado);
			representacion.append("}");
		}
		
		return representacion.toString();
	}
}
