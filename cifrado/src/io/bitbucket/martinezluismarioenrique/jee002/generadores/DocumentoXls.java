package io.bitbucket.martinezluismarioenrique.jee002.generadores;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

public final class DocumentoXls {

	public static void deLibros(HttpServletResponse response, MensajeCifrado mensaje, int opcion) throws IOException {

		HSSFWorkbook libroTrabajo = new HSSFWorkbook();
		HSSFSheet hoja = libroTrabajo.createSheet("Mensaje");

		/* Primera fila */
		int numeroFila = 0;
		HSSFRow fila = hoja.createRow(numeroFila);

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
			HSSFCell celdaCabecera = fila.createCell(numeroCabecera);
			celdaCabecera.setCellValue(cabecera);
			numeroCabecera += 1;
		}

		fila = hoja.createRow(1);
		fila.createCell(0).setCellValue(mensaje.getMensaje());
		fila.createCell(1).setCellValue(mensaje.getDesplazamiento());
		fila.createCell(2).setCellValue(mensaje.getMensajeCifrado());

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Mensaje.xls");
		ServletOutputStream salida = response.getOutputStream();
		libroTrabajo.write(salida);

		libroTrabajo.close();

		// salida.close();

	}
	
}