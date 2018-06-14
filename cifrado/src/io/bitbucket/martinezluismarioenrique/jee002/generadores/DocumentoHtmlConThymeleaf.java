package io.bitbucket.martinezluismarioenrique.jee002.generadores;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import io.bitbucket.martinezluismarioenrique.jee002.utilities.Console;

public final class DocumentoHtmlConThymeleaf {

	private static final String SUJETO = "DocumentoHtmlConThymeleaf";

	public static void comoRespuesta(HttpServletRequest request, HttpServletResponse response, String mensaje,			
			String cifrado, String desplazamiento, String error1, String error2, boolean descarga, int opcion)
			throws IOException {

		Console.println(SUJETO, "Atendiendo solicitud y generando respuesta...");

		/* Obtención de DATOS */
		Map<String, Object> datos = Datos.formulario(request, mensaje,			
				cifrado, desplazamiento, error1, error2, descarga);

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		ServletContext contextoServlet = request.getServletContext();

		/* Configuración de Thymlead */
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(contextoServlet);
		/*
		 * Modo en el que se procesará la plantilla: HTML, XML, XHTML, CSS, JAVASCRIPT, etc
		 *
		 * Para más información:
		 *   http://www.thymeleaf.org/apidocs/thymeleaf/3.0.6.RELEASE/org/thymeleaf/templatemode/TemplateMode.html
		 * */
		templateResolver.setTemplateMode(TemplateMode.HTML);
		/* Directorio en donde se buscarán las plantillas */
		templateResolver.setPrefix("/WEB-INF/thymeleaf/");
		/* Codificación de caracteres de la plantilla */
		templateResolver.setCharacterEncoding("UTF-8");
		/*
		 * Extensión de la plantilla.
		 * Al momento de procesar la plantilla solo se indica su nombre
		 * (sin extensión), templateEngine.process() más abajo.
		 */
		templateResolver.setSuffix(".html");
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
		templateResolver.setCacheable(false);

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		/*
		 * DATOS compartidos-enviados a /WEB-INF/thymeleaf/DocumentoHtml.html
		 * haciendo uso del objeto ctx (WebContext).
		 */
		WebContext ctx = new WebContext(request, response, contextoServlet);
		ctx.setVariable("datos", datos);

		/*
		 * Se procesa la plantilla compartiendole los DATOS y el resultado es enviado como respuesta
		 * al cliente (navegador web).
		 */
		if(opcion == 1) {
			templateEngine.process("HtmlCifradoDesplazamiento", ctx, response.getWriter());
		}else if(opcion == 2) {
			templateEngine.process("HtmlCifradoDesplazamientoDescarga", ctx, response.getWriter());
		}else if(opcion == 3) {
			templateEngine.process("HtmlCifradoVigenere", ctx, response.getWriter());
		}else if(opcion == 4) {
			templateEngine.process("HtmlCifradoVigenereDescarga", ctx, response.getWriter());
		}else if(opcion == 5) {
			templateEngine.process("HtmlDescifrarVigenere", ctx, response.getWriter());
		}else if(opcion == 6) {
			templateEngine.process("HtmlDescifrarVigenereDescarga", ctx, response.getWriter());
		}
		

		Console.println(SUJETO, "Respuesta generada y solicitud atendida.");

	}
}