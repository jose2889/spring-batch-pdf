package com.hernandez.batch.configuration;

import com.hernandez.batch.dao.entity.Cliente;
import com.hernandez.batch.dao.entity.Voltage;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class VoltageProcessor implements ItemProcessor<Cliente, Voltage> {

	@Override
	public Voltage process(final Cliente cliente) throws DocumentException, FileNotFoundException {
		final String nombre = cliente.getNombre();
		final String apellido = cliente.getApellido();
		final Logger log = LoggerFactory.getLogger(VoltageProcessor.class);
		String user = cliente.getId().toString();
		String admin = "admin";
		// se genera un pdf
		Document document = new Document();

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("EstadoDeCuenta" + cliente.getNombre() + ".pdf"));
		writer.setEncryption(admin.getBytes(), user.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
		PdfPTable table = new PdfPTable(3);
		Font font = new Font(Font.HELVETICA, 12, Font.BOLDITALIC);
		final Voltage processedVoltage = new Voltage(); 
		try {
			  Image image = Image.getInstance("descarga.png");
			  PdfPCell cell = new PdfPCell();
				cell.setBackgroundColor(new Color(184, 218, 255));
				cell.setPhrase(new Phrase("Nombre", font));
				table.addCell(cell);
				cell.setPhrase(new Phrase("Apellido", font));
				table.addCell(cell);
				cell.setPhrase(new Phrase("Email", font));
				table.addCell(cell);

				PdfPTable table2 = new PdfPTable(3);
				table2.addCell(cliente.getNombre());
				table2.addCell(cliente.getApellido());
				table2.addCell(cliente.getEmail());
				document.open();
				document.newPage();
				// adding table to document
				document.add(table);
				document.add(table2);
				document.add(image);
				document.close();
				writer.close();
				log.info("PDF usando OpenPDF creado con exito");

				
				processedVoltage.setNombre(nombre);
				processedVoltage.setApellido(apellido);
		} catch (Exception e) {
			log.info("OCURRIO UN ERROR EN EL PROCESO DE CREAR EL PDF",e);
		}
		
		
		return processedVoltage;
	}
}
