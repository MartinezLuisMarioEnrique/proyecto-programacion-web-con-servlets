package io.bitbucket.martinezluismarioenrique.jee002.generadores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public final class Datos {

	/*
	 * Método para obtener el *uptime* o tiempo transcurrido desde que la computadora-servidor
	 * fue encendida.
	 *
	 * Se ejecuta el comando *uptime -p* del sistema operativo.
	 *
	 * El comando *uptime* solo estaría disponible en sistemas operativos tipo
	 * GNU/Linux-Unix.
	 */
	private static String uptime() {

		StringBuffer resultado = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec("uptime -p");
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				resultado.append(line + "\n");
				line = reader.readLine();
			}
		} catch (Exception e) {
			resultado.append("");
		}

		if (!resultado.toString().isEmpty()) {
			return resultado.toString()
					.substring(3)
					.replaceFirst("hour", "hora")
					.replaceFirst("hours", "horas")
					.replaceFirst("minute", "minuto")
					.replaceFirst("minutes", "minutos");
		}

		return resultado.toString();
	}

	public static Map<String, Object> formulario(HttpServletRequest request, String mensaje,			
			String cifrado, String desplazamiento, String error1, String error2, boolean descarga) {

		/*
		 * Los DATOS se representan mediante la interfaz Map haciendo uso de la
		 * estructura de datos HashMap:
		 *
		 *
		 *    [datos]-------
		 *                 |
		 *                 v
		 *
		 *                 ----------------------------------------------------------------
		 *                 |            llave             |             valor             |
		 *                 ----------------------------------------------------------------
		 *                 |          "numeroA"           |  new NumeroPseudoAleatorio()  |
		 *                 |          "numeroB"           |  new NumeroPseudoAleatorio()  |
		 *                 |          "numeroC"           |  new NumeroPseudoAleatorio()  |
		 *                 |  "numerosPseudoAleatorios"   |  List<NumeroPseudoAleatorio>  |
		 *                 | "solicitudHttpRequestMethod" |        "GET" o "POST"         |
		 *                 |             ...              |             ...               |
		 *                 |  "solicitudHttpQueryString"  |        "mediante=jsp"         |
		 *                 |             ...              |             ...               |
		 *                 |          "uptime"            |     "1 hora, 31 minutos"      |
		 *                 |             ...              |             ...               |
		 *                 ----------------------------------------------------------------
		 *
		 * Para guardar datos:
		 *
		 *    datos.put(String llave, Object valor);
		 *
		 *    Por ejemplo:
		 *
		 *        List<Perro> chilaquiles = new ArrayList<>();
		 *        datos.put("perros", chilaquiles);
		 *
		 * Para leer datos:
		 *
		 *    ClaseConcretaOInterfaz valor = (ClaseConcretaOInterfaz) datos.get("llave")
		 *
		 *    Por ejemplo:
		 *
		 *        List<Perro> perros = (List<Perro>) datos.get("chilaquiles");
		 *
		 * Los DATOS podría ser obtenidos de igual forma de otra *FUENTE DE DATOS*, como
		 * un archivo de texto plano (CSV, XML, JSON, etc), un sistema gestor de base de
		 * datos (PostgreSQL, MariaDB, etc) o inclusive datos enviados desde un
		 * formulario de HTML en solicitudes POST.
		 *
		 * Para más información:
		 *   https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
		 *   http://www.w3api.com/wiki/Java:HashMap
		 *   http://datojava.blogspot.mx/2016/07/queEsHashMapYComoFunciona.html
		 */
		Map<String, Object> datos = new HashMap<String, Object>();
			datos.put("mensaje", mensaje);
			datos.put("cifrado", cifrado);
			datos.put("desplazamiento", desplazamiento);
			datos.put("error1", error1);
			datos.put("error2", error2);
		
		
		

		

		SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEE d 'de' MMMM 'del' yyyy", new Locale("es", "MX"));
		String fecha = formatoFecha.format(new Date());
		SimpleDateFormat formatoHora = new SimpleDateFormat("H:mm:ss a", new Locale("es", "MX"));
		String hora = formatoHora.format(new Date());

		datos.put("uptime", uptime());
		datos.put("fecha", fecha);
		datos.put("hora", hora);

		return datos;
	}
}
