package io.bitbucket.martinezluismarioenrique.jee002.generadores;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

public final class DocumentoXlsx {

	public static void deLibros(HttpServletResponse response, MensajeCifrado mensaje, int opcion) throws IOException {

		XSSFWorkbook libroTrabajo = new XSSFWorkbook();
		XSSFSheet hoja = libroTrabajo.createSheet("Mensaje");

		/* Primera fila */
		int numeroFila = 0;
		XSSFRow fila = hoja.createRow(numeroFila);
		String[] cabeceras = new String [3];
		if(opcion == 1) {
			cabeceras [0] = "mensaje";
			cabeceras [1] = "desplazamiento";
			cabeceras [2] = "mensajeCifrado";
		}else if(opcion == 2) {
			cabeceras [0] = "mensaje";
			cabeceras [1] = "clave";
			cabeceras [2] = "mensajeCifrado";
		}else if (opcion == 3) {
			cabeceras [0] = "mensaje";
			cabeceras [1] = "clave";
			cabeceras [2] = "mensajeDescifrado";
		}
		
		int numeroCabecera = 0;
		for (String cabecera : cabeceras) {
			XSSFCell celdaCabecera = fila.createCell(numeroCabecera);
			celdaCabecera.setCellValue(cabecera);
			numeroCabecera += 1;
		}

		fila = hoja.createRow(1);
		fila.createCell(0).setCellValue(mensaje.getMensaje());
		fila.createCell(1).setCellValue(mensaje.getDesplazamiento());
		fila.createCell(2).setCellValue(mensaje.getMensajeCifrado());

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=Mensaje.xlsx");
		ServletOutputStream salida = response.getOutputStream();
		libroTrabajo.write(salida);

		libroTrabajo.close();

		// salida.close();

	}
}