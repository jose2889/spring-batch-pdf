package com.techshard.batch.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techshard.batch.dao.entity.Archivo;
import com.techshard.batch.services.GenerarEstadoCuentaService;

import com.techshard.batch.services.PdfService;

@RestController
@RequestMapping("/api")
public class BatchRestController {

	private static final Logger LOG = LoggerFactory.getLogger(BatchRestController.class);
	
	@Autowired
	private GenerarEstadoCuentaService generarEstadoCuentaService; 
	
	@Autowired
	private PdfService pdf; 
	
	
	@GetMapping("/batch-facturacion")
	@ResponseStatus(code = HttpStatus.OK)
	public String index(){
		
		try {
			generarEstadoCuentaService.executeJob();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.debug("OCURRIO UN ERROR AL MOMENTO DE EJECUTAR EL JOB");
			e.printStackTrace();
		}
		return "Se Ejecuto el Job";
	}
	
	@GetMapping("/pdf")
	@ResponseStatus(code = HttpStatus.OK)
	public String generarPdf(){
		
		try {
			pdf.PdfDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.debug("OCURRIO UN ERROR AL MOMENTO DE GENERAR EL PDF");
			e.printStackTrace();
		}
		return "se ha generado el PDF con exito";
	}
	
	@GetMapping("/listar")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Archivo> listarArchivos(){
		
		List<Archivo>  resultado = null; 
		try {
			resultado = pdf.listarArchivos();
			LOG.debug("resultadoF", resultado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.debug("OCURRIO UN ERROR AL MOMENTO DE LISTAR EL PDF");
			e.printStackTrace();
		}
		return resultado;
	}
}
