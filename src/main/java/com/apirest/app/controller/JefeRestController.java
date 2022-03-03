package com.apirest.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.app.entity.Jefe;
import com.apirest.app.service.JefeService;

@RestController
@RequestMapping("/api")
public class JefeRestController {

	@Autowired
	private JefeService jefeService;
	
	@GetMapping("/jefes")
	public List<Jefe> index(){
		return jefeService.finAll();
	}
	
	@GetMapping("/jefes/{id}")
	public ResponseEntity<?> findJefeById(@PathVariable Long id) {
		
		Jefe jefe = null;
		Map<String,Object> response = new HashMap<>();
		
		try	{
			jefe = jefeService.findById(id);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(jefe == null) {
			response.put("mensaje", "El jefe con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Jefe>(jefe, HttpStatus.OK);
	}
	
	@PostMapping("/jefe")
	public ResponseEntity<?> saveJefe(@RequestBody Jefe jefe)	{
		
		Jefe jefeNuevo = null;
		Map<String,Object> response = new HashMap<>();
		
		try	{
			jefeNuevo = jefeService.save(jefe);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar una insert a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El jefe ha sido creada con éxito");
		response.put("jefe", jefeNuevo);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/jefes/{id}")
	public ResponseEntity<?> updateJefe(@RequestBody Jefe jefe, @PathVariable Long id) {
		
		Jefe jefeActual = jefeService.findById(id);	
		Map<String,Object> response = new HashMap<>();
		
		if(jefeActual == null) {
			response.put("mensaje", "No se puede editar el jefe, el ID ".concat(id.toString().concat(" no existe en la base de datos")));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {		
			jefeActual.setNombre(jefe.getNombre());
			jefeActual.setDni(jefe.getDni());
			jefeActual.setDepartamento(jefe.getDepartamento());
			jefeActual.setSalario(jefe.getSalario());
			jefeActual.setTelefono(jefe.getTelefono());
			
			jefeService.save(jefeActual);
			
		} catch(DataAccessException e){			
			response.put("mensaje", "Error al realizar un update a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El jefe ha sido actualizado con éxito");
		response.put("jefe", jefeActual);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/jefes/{id}")
	public ResponseEntity<?> deleteJefe(@PathVariable Long id) {
		
		Jefe jefeEliminado = jefeService.findById(id);	
		Map<String,Object> response = new HashMap<>();
		
		if(jefeEliminado == null) {
			response.put("mensaje", "No se puede eliminar el jefe, el ID ".concat(id.toString()).concat(" no existe en la base de datos"));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			
		}
		
		try {
			jefeService.delete(id);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar un delete a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El jefe ha sido eliminado con éxito");
		response.put("jefe", jefeEliminado);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}
