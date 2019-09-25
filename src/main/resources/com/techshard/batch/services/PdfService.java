package com.techshard.batch.services;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.techshard.batch.dao.entity.Archivo;

@Service
public class PdfService {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";

	public void PdfDocument() throws Exception {
		// TODO Auto-generated method stub
		Document document = new Document();

//		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("prueba.pdf"));
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("sample.pdf"));
		PdfPTable table = new PdfPTable(4);
		table.addCell("esto es una prueba");
		table.addCell("funciono");
		table.addCell("esto es una prueba");
		table.addCell("esto es una prueba");
		document.open();
		document.newPage();
		// adding table to document
		document.add(table);
		document.close();
		writer.close();
		System.out.println("PDF using OpenPDF created successfully");

	}

	public List<Archivo> listarArchivos() {

		List<Archivo> datos = new ArrayList<>(); 
	
		 //Carpeta del usuario
	    String sCarpAct = System.getProperty("user.dir");
	    System.out.println("Carpeta del usuario = " + sCarpAct);

	    File carpeta = new File(sCarpAct);
	  


	    File[] archivos = carpeta.listFiles();
	    
	    
	    //Se pueden filtrar los resultados tanto usando list() como usando listFiles()
	    //Por ejemplo, en este segundo caso para mostrar solo archivos y no carpetas
	    System.out.println(ANSI_RED + "//// LISTADO CON FILTRO APLICADO - SOLO ARCHIVOS" + ANSI_RESET);

	    FileFilter filtro = new FileFilter() {
	      @Override
	      public boolean accept(File arch) {
	        return arch.isFile();
	      }
	    };

	    archivos = carpeta.listFiles(filtro);

	    if (archivos == null || archivos.length == 0) {
	      System.out.println("No hay elementos dentro de la carpeta actual");
	      return datos;
	    }
	    else {
	      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	      for (int i=0; i< archivos.length; i++) {
	        File archivo = archivos[i];
	        Archivo dato = new Archivo(); 
	        dato.setNombre(archivo.getName());
	        dato.setRuta(archivo.getPath());
	        dato.setPeso(archivo.length());
	        dato.setTipo(archivo.isDirectory() ? "Carpeta" : "Archivo");
	        dato.setFecha(sdf.format(archivo.lastModified()));
	        datos.add(dato);
	        System.out.println(datos);
	      }
	    }
		return datos;

	  }
	
}
