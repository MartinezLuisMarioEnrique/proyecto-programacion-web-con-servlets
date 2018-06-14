package io.bitbucket.MartinezLuisMarioEnrique.jee002.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoCsv;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoHtmlConThymeleaf;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoJson;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoPdf;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoXls;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoXlsx;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoXml;
import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

/**
 * Servlet implementation class CifradoVigenere
 */
@WebServlet("/CifradoVigenere")
public class CifradoVigenere extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final char[] alfabetoVigenere = { 'a', 'á', 'b', 'c', 'd', 'e', 'é', 'f', 'g', 'h', 'i', 'í', 'j', 'k', 'l',
			'm', 'n', 'ñ', 'o', 'ó', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ü', 'v', 'w', 'x', 'y', 'z' };
	String mensaje = "", clave = "", cifrado = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CifradoVigenere() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.mostrarFormularioHtml(request, response, "", "", "", "", "", false, 3);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String formatoSolicitado = request.getParameter("formato");
		if (formatoSolicitado == null) {
			formatoSolicitado = "";
		}

		/* Obtención de Datos */
		MensajeCifrado mensajeCifrado = new MensajeCifrado(mensaje, clave, cifrado);

		switch (formatoSolicitado) {
		case "Descargar CSV":
			response.setCharacterEncoding("UTF-8");
			PrintWriter salida = response.getWriter();						
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "inline; filename=\"libros.csv\"");
			// response.setHeader("Content-Disposition", "attachment;
			// filename=\"libros.csv\"");
			salida.println(DocumentoCsv.deLibros(mensajeCifrado, 2));
			break;
		case "Descargar XML":
			response.setCharacterEncoding("UTF-8");
			PrintWriter salida2 = response.getWriter();
			response.setContentType("application/xml");
			response.setHeader("Content-Disposition", "inline; filename=\"libros.xml\"");
			// response.setHeader("Content-Disposition", "attachment;
			// filename=\"libros.xml\"");
			salida2.println(DocumentoXml.deLibros(mensajeCifrado, 2));
			break;
		case "Descargar JSON":
			response.setCharacterEncoding("UTF-8");
			PrintWriter salida3 = response.getWriter();
			response.setContentType("application/json");
			response.setHeader("Content-Disposition", "inline; filename=\"libros.json\"");
			// response.setHeader("Content-Disposition", "attachment;
			// filename=\"libros.json\"");
			salida3.println(DocumentoJson.deLibros(mensajeCifrado, 2));
			break;
		case "Descargar XLS":
			/* Microsoft Excel Worksheet */
			DocumentoXls.deLibros(response, mensajeCifrado, 2);
			break;
		case "Descargar XLSX":
			/* Microsoft 2007 spreadsheet */
			DocumentoXlsx.deLibros(response, mensajeCifrado, 2);
			break;
		case "Descargar PDF":
			/* PDF Document */
			DocumentoPdf.deLibros(response, mensajeCifrado, 2);
			break;
		default:
			this.procesarFormularioHtml(request, response);
			break;
		}

	}

	private void mostrarFormularioHtml(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensaje,
			String cifrado, String clave, String error1, String error2, boolean descarga, int opcion)
			throws IOException {
		DocumentoHtmlConThymeleaf.comoRespuesta(solicitud, respuesta, mensaje, cifrado, clave, error1, error2, descarga,
				opcion);

	}

	private void procesarFormularioHtml(HttpServletRequest request, HttpServletResponse response) throws IOException {

		boolean band = true;
		String e1 = "", e2 = "";
		try {
			String mensajedoPost = request.getParameter("textarea-mensaje");
			clave = request.getParameter("text");

			mensaje = mensajedoPost.replace("<", "&lt;");
			mensaje = mensaje.replace(">", "&gt;");

			if (mensaje.equals("")) {
				band = false;
				e1 = "* El mensaje no debe estar vacio";
			}

			if (mensaje.length() > 2000) {
				band = false;
				e1 = "* El mensaje no puede exceder los 2000 caracteres";
			}

			if (mensaje.equals("")) {
				band = false;
				e2 = "* La clave no puede estar vacía";
			} 
			
			if(!Conversion.esTexto(clave)) {
				e2 = "* La clave no puede contener digitos";
			}

			if (e1.length() > 0 || e2.length() > 0) {
				this.mostrarFormularioHtml(request, response, "", "", clave, e1, e2, false, 3);
			}

			String cif = cifrar(mensaje, clave);

			cifrado = cif.replace("<", "&lt;");
			cifrado = cifrado.replace(">", "&gt;");

			if (cifrado.equals("0")) {
				e2 = "* ERROR EN LA CLAVE INGRESADA: Un o más caracteres no pertenece al alfabeto.";
			}

			if (band) {

				this.mostrarFormularioHtml(request, response, mensaje, cifrado, clave, "", "", true, 4);
			}

		} catch (NullPointerException ex) {
			this.mostrarFormularioHtml(request, response, "", "", "", "", "", false, 3);
		}
	}

	// Retorna True si el caracter estÃ¡ en MayÃºscula
	protected boolean isUpperCase(char e) {
		return Character.isUpperCase(e);
	}

	// Recupera el Ã­ndice del caracter a encriptar, en el vector
	protected int recuperarIndiceActual(char e) {
		char aux = isUpperCase(e) ? Character.toLowerCase(e) : e;

		for (int i = 0; i < alfabetoVigenere.length; i++) {
			if (alfabetoVigenere[i] == aux) // Si el caracter existe en el alfabeto
				return i;
		}
		return -1; // El caracter no existe en el alfabeto
	}

	// Determina el Ã­ndice del caracter encriptado que sustituirÃ¡ al caracter
	// original
	protected int calcularNuevoIndice(int x, int k) {
		// El valor de alfabetoVigenere.length es 33
		int E = (x + k) % 33; // FunciÃ³n de desplazamiento

		if (E >= 33)
			return E - 33;
		else if (E < 0)
			return 33 + E;
		else
			return E;
	}

	// Regresa el caracter correspondiente tras realizar el desplazamiento
	protected char obtenerNuevoCaracter(char e, char caracterClave, boolean descifrar) {
		int posActual = recuperarIndiceActual(e);
		int posClave = descifrar ? -recuperarIndiceActual(caracterClave) : recuperarIndiceActual(caracterClave);

		// Si el caracter del mensaje a cifrar no existe en el alfabeto, lo devuelve sin
		// cambios
		if (posActual < 0)
			return e;

		// Si el caracter existe en el alfabeto y estÃ¡ en MAYÃSCULA, regresa el nuevo
		// caracter en MAYÃSCULA
		if (isUpperCase(e))
			return Character.toUpperCase(alfabetoVigenere[calcularNuevoIndice(posActual, posClave)]);

		// Si el caracter existe en el alfabeto y estÃ¡ en minÃºscula, regresa el nuevo
		// caracter en minÃºscula
		return alfabetoVigenere[calcularNuevoIndice(posActual, posClave)];
	}

	// Devuelve el mensaje cifrado
	protected String cifrar(String message, String clv) {
		char[] mensaje = Conversion.aChar(message);
		char[] clave = Conversion.aChar(clv);
		String mensajeCifrado = "";
		int claveIndex = 0;
		char caracter;

		/* Validamos que todos los caracteres de la clave sean parte del alfabeto */
		for (int j = 0; j < clave.length; j++) {
			if (recuperarIndiceActual(clave[j]) < 0) {
				return "0";
			}
		}

		/* Formamos el nuevo mensaje cifrado */
		for (int a = 0; a < mensaje.length; a++) {

			/* Para el descifrado pasar True en lugar de False */
			caracter = obtenerNuevoCaracter(mensaje[a], clave[claveIndex], false);

			mensajeCifrado += caracter;

			// Si el caracter que se va a cifrar existe en el alfabeto
			if (recuperarIndiceActual(caracter) > -1) {
				claveIndex++; // Recorrido en la clave
			}

			// Si si alcanza el ultimo caracter de la clave en el recorrido de la misma
			if (claveIndex >= clave.length) {
				claveIndex = 0; // Volvemos al inicio
			}

		}
		return mensajeCifrado;
	}

}
