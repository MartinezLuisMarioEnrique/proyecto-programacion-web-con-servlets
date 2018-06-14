package io.bitbucket.MartinezLuisMarioEnrique.jee002.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoPdf;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoXls;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoXlsx;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoHtmlConThymeleaf;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoCsv;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoJson;
import io.bitbucket.martinezluismarioenrique.jee002.generadores.DocumentoXml;
import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

/**
 * Servlet implementation class FormularioHTML
 */

@WebServlet("/CifradoDesplazamiento")

public class CifradoDesplazamiento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String[] alfabeto = { "a", "á", "b", "c", "d", "e", "á", "f", "g", "h", "i", "í", "j", "k", "l", "m",
			"n", "ñ", "o", "ó", "p", "q", "r", "s", "t", "u", "ú", "ü", "v", "w", "x", "y", "z" };

	String mensaje = "", desplazamiento = "", cifrado = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CifradoDesplazamiento() {
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
		this.mostrarFormularioHtml(request, response, "", "", "", "", "", false, 1);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String formatoSolicitado = request.getParameter("formato");
		if (formatoSolicitado == null) {
			formatoSolicitado = "";
		}

		/* Obtención de Datos */
		MensajeCifrado mensajeCifrado = new MensajeCifrado(mensaje, desplazamiento, cifrado);

		switch (formatoSolicitado) {
		case "Descargar CSV":
			response.setCharacterEncoding("UTF-8");
			PrintWriter salida = response.getWriter();
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "inline; filename=\"libros.csv\"");
			// response.setHeader("Content-Disposition", "attachment;
			// filename=\"libros.csv\"");
			salida.println(DocumentoCsv.deLibros(mensajeCifrado, 1));
			break;
		case "Descargar XML":
			response.setCharacterEncoding("UTF-8");
			PrintWriter salida2 = response.getWriter();
			response.setContentType("application/xml");
			response.setHeader("Content-Disposition", "inline; filename=\"libros.xml\"");
			// response.setHeader("Content-Disposition", "attachment;
			// filename=\"libros.xml\"");
			salida2.println(DocumentoXml.deLibros(mensajeCifrado, 1));
			break;
		case "Descargar JSON":
			response.setCharacterEncoding("UTF-8");
			PrintWriter salida3 = response.getWriter();
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Content-Disposition", "inline; filename=\"libros.json\"");
			// response.setHeader("Content-Disposition", "attachment;
			// filename=\"libros.json\"");
			salida3.println(DocumentoJson.deLibros(mensajeCifrado, 1));
			break;
		case "Descargar XLS":
			/* Microsoft Excel Worksheet */
			DocumentoXls.deLibros(response, mensajeCifrado, 1);
			break;
		case "Descargar XLSX":
			/* Microsoft 2007 spreadsheet */
			DocumentoXlsx.deLibros(response, mensajeCifrado, 1);
			break;
		case "Descargar PDF":
			/* PDF Document */
			DocumentoPdf.deLibros(response, mensajeCifrado, 1);
			break;
		default:
			this.procesarFormularioHtml(request, response);
			break;
		}
	}

	private void mostrarFormularioHtml(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensaje,
			String cifrado, String desplazamiento, String error1, String error2, boolean descarga, int opcion)
			throws IOException {

		DocumentoHtmlConThymeleaf.comoRespuesta(solicitud, respuesta, mensaje, cifrado, desplazamiento, error1, error2,
				descarga, opcion);

	}

	private void procesarFormularioHtml(HttpServletRequest request, HttpServletResponse response) throws IOException {

		boolean band = true;
		String e1 = "", e2 = "";
		try {
			String mensajedoPost = request.getParameter("textarea-mensaje");
			desplazamiento = request.getParameter("input-number");

			mensaje = mensajedoPost.replace("<", "&lt;");
			mensaje = mensaje.replace(">", "&gt;");
			boolean descarga = false;

			if (mensaje.equals("")) {
				band = false;
				e1 = "* El mensaje no debe estar vacio";
			}
			if (desplazamiento.equals("")) {
				band = false;
				e2 = "* ERROR: El valor de desplazamiento no puede estar vacío";

			} else if (!Conversion.esEntero(desplazamiento)) {
				band = false;
				e2 = "* ERROR: El valor de desplazamiento no pudo ser convertido a entero";

			} else if (Conversion.aInt(desplazamiento) < -33 || Conversion.aInt(desplazamiento) > 33) {
				band = false;
				e2 = "* ERROR: El valor de desplazamiento debe estar entre -32 y +32";

			}

			if (e1.length() > 0 || e2.length() > 0) {
				this.mostrarFormularioHtml(request, response, mensaje, "", desplazamiento, e1, e2, false, 1);
			}

			String cif = algoritmoCesar(mensaje, desplazamiento);

			cifrado = cif.replace("<", "&lt;");
			cifrado = cifrado.replace(">", "&gt;");

			if (band) {
				descarga = true;
				this.mostrarFormularioHtml(request, response, mensaje, cifrado, desplazamiento, "", "", descarga, 2);
				descarga = false;
			}

		} catch (NullPointerException ex) {
			this.mostrarFormularioHtml(request, response, "", "", "", "", "", false, 1);
		}
	}

	protected String algoritmoCesar(String message, String desplazamiento) {
		/*
		 * TAREA: CIFRADO DE DESPLAZAMIENTO Sea: ?mensaje=x&desplazamiento=y
		 * 
		 * Donde: x = texto a cifrar en base al algoritmo de desplazamiento y = n�mero
		 * entero (+/-) en el rango de la cantidad de caracteres en el alfabeto a usar
		 * (-33,...,0,...,33) Alfabeto:
		 * a,�,b,c,d,e,�,f,g,h,i,�,j,k,l,m,n,�,o,�,p,q,r,s,t,u,�,�,v,w,x,y,z
		 * 
		 * ?mensaje=hola&desplazamiento=5 --> ls�e ?mensaje=HOLA&desplazamiento=3 -->
		 * JQ�C ?mensaje=hOlA&desplazamiento=7 --> nUqF ?mensaje=hola
		 * mundo&desplazamiento=2 --> �pnb ��o�p ?mensaje=H0L@ mUnd0&desplazamiento=4
		 * -->K0O@ �Wpg0 ?mensaje=roma&desplazamiento=30 --> �mjx
		 * ?mensaje=ls�e&desplazamiento=-5 --> hola
		 * 
		 * NOTAS: Se respetan may�sculas y caracteres que no est�n dentro del alfabeto
		 */

		// http://localhost:8080/jee02/Mensaje?mensaje=Salutaciones&desplazamiento=30

		String criptedMessage = "";
		int displacement = Conversion.aInt(desplazamiento);

		int index = 0;
		while (index < message.length()) {
			criptedMessage += getEncryptedElement(String.valueOf(message.charAt(index)), displacement);
			index++;
		}

		return criptedMessage;
	}

	// Retorna el nuevo elemento encriptado (resultante del desplazamiento)
	protected String getEncryptedElement(String element, int displacement) {
		// Verifica si el elemento est� en may�scula
		boolean isUpperCase = element.equals(element.toUpperCase());
		String newElement = "";

		// Si el elemento est� dentro del alfabeto, regresa el nuevo elemento encriptado
		// respetando may�sculas
		for (int i = 0; i < alfabeto.length; i++) {
			if (alfabeto[i].equalsIgnoreCase(element)) {
				newElement = (isUpperCase) ? alfabeto[getNewIndex(i, displacement)].toUpperCase()
						: alfabeto[getNewIndex(i, displacement)];
				return newElement;
			}
		}

		// Si el elemento no est� dentro del alfabeto, lo retorna sin cambios
		return element;
	}

	// Determina el �ndice del elemento encriptado en el vector
	protected int getNewIndex(int currentIndex, int displacement) {
		// alfabeto.length = 33
		if (currentIndex + displacement >= alfabeto.length)
			return currentIndex + displacement - alfabeto.length;
		else if (currentIndex + displacement < 0)
			return alfabeto.length + (currentIndex + displacement);
		else
			return currentIndex + displacement;
	}
}
