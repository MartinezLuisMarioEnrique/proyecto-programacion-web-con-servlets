package io.bitbucket.martinezluismarioenrique.jee002.generadores;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import io.bitbucket.martinezluismarioenrique.jee002.pojos.MensajeCifrado;

public final class DocumentoPdf {

	public static void deLibros(HttpServletResponse response, MensajeCifrado mensaje, int opcion) throws IOException {
		String HCRBATANG = "/HANBatang.ttf";
		PdfFont fontUnicode = PdfFontFactory.createFont(HCRBATANG, PdfEncodings.IDENTITY_H, true);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=Mensaje.pdf");
		response.setCharacterEncoding("UTF-8");
		ServletOutputStream salida = response.getOutputStream();
		PdfDocument archivoPdf = new PdfDocument(new PdfWriter(salida));

		Document documentoPdf = new Document(archivoPdf);
		Table tabla = new Table(new float[] { 40.5f, 5.5f, 40.5f });

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
		
		for (String cabecera : cabeceras) {
			tabla.addCell(cabecera);
		}

		tabla.addCell(String.valueOf(mensaje.getMensaje())).setFont(fontUnicode);
		tabla.addCell(String.valueOf(" "+mensaje.getDesplazamiento())).setFont(fontUnicode);
		tabla.addCell(String.valueOf(mensaje.getMensajeCifrado())).setFont(fontUnicode);
		documentoPdf.add(tabla);
		documentoPdf.close();

		// salida.close();
	}
}