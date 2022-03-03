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

import com.apirest.app.entity.Departamento;
import com.apirest.app.entity.Empleado;
import com.apirest.app.entity.Jefe;
import com.apirest.app.service.DepartamentoService;

/**
 * @author psariego
 *
 */
@RestController
@RequestMapping("/api")
public class DepartamentoRestController {
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping("/departamentos")
	public List<Departamento> index(){
		return departamentoService.findAll();
	}
	
	@GetMapping("/departamentos/{id}")
	public ResponseEntity<?> findDepartamentoById(@PathVariable Long id) {
		
		Departamento departamento = null;
		Map<String,Object> response = new HashMap<>();
		
		try	{
			departamento = departamentoService.findById(id);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(departamento == null) {
			response.put("mensaje", "El departamento con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Departamento>(departamento, HttpStatus.OK);
	}
	
	@PostMapping("/departamento")
	public ResponseEntity<?> saveDepartamento(@RequestBody Departamento departamento)	{
		
		Departamento departamentoNuevo = null;
		Map<String,Object> response = new HashMap<>();
		
		try	{
			departamentoNuevo = departamentoService.save(departamento);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar una insert a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El departamento ha sido creada con éxito");
		response.put("departamento", departamentoNuevo);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/departamentos/{id}")
	public ResponseEntity<?> updateDepartamento(@RequestBody Departamento departamento, @PathVariable Long id) {
		
		Departamento departamentoActual = departamentoService.findById(id);	
		Map<String,Object> response = new HashMap<>();
		
		if(departamentoActual == null) {
			response.put("mensaje", "No se puede editar el departamento, el ID ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {		
			departamentoActual.setNombre(departamento.getNombre());
			departamentoActual.setUbicacion(departamento.getUbicacion());
			departamentoService.save(departamentoActual);
			
		} catch(DataAccessException e){			
			response.put("mensaje", "Error al realizar un update a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El departamento ha sido actualizado con éxito");
		response.put("departamento", departamentoActual);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/departamentos/{id}")
	public ResponseEntity<?> deleteDepartamento(@PathVariable Long id) {
		
		Departamento departamentoEliminado = departamentoService.findById(id);	
		Map<String,Object> response = new HashMap<>();
		
		if(departamentoEliminado == null) {
			response.put("mensaje", "No se puede eliminar el departamento, el ID ".concat(id.toString()).concat(" no existe en la base de datos"));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			
		}
		
		try {
			departamentoService.delete(id);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar un delete a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El departamento ha sido eliminado con éxito");
		response.put("departamento", departamentoEliminado);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/departamentos/empleados")
	public List<Empleado>listarEmpleados() {
		return departamentoService.findAllEmpleados();
	}
	
	@GetMapping("/departamentos/jefes")
	public List<Jefe>listarJefes(){
		return departamentoService.findAllJefes();
	}
 
}
