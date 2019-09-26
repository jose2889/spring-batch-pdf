package com.hernandez.batch.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hernandez.batch.dao.entity.Archivo;
import com.hernandez.batch.services.GenerarEstadoCuentaService;
import com.hernandez.batch.services.PdfService;

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
	@PostMapping("upload")
	public String guardar(@RequestParam("file") MultipartFile foto) {
		
	
		if (!foto.isEmpty()) {

			String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
			Path rootPath = Paths.get("pdf").resolve(uniqueFilename);

			Path rootAbsolutPath = rootPath.toAbsolutePath();
			
			LOG.info("rootPath: " + rootPath);
			LOG.info("rootAbsolutPath: " + rootAbsolutPath);

			try {

				Files.copy(foto.getInputStream(), rootAbsolutPath);
				

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return "Se guardo la imagen con exito";
	}
	
//	 @GetMapping("/solicitud/{archivo}/descargar-pdf")
//	    @Timed
//	    public ResponseEntity<Resource> descargarPdf(@PathVariable String archivo){
//	        log.debug("REST GET descargarPdf " + archivo);
//
//	        Resource resource = storageService.loadPdfFromTmpFile(archivo+".pdf");
//
//	        return ResponseEntity
//	            .ok()
//	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archivo + ".pdf")
//	            .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
//	            .body(resource);
//	    }
}
