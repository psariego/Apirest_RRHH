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

import com.apirest.app.entity.Empleado;
import com.apirest.app.service.EmpleadoService;

@RestController
@RequestMapping("/api")
public class EmpleadoRestController {
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@GetMapping("/empleados")
	public List<Empleado> index(){
		return empleadoService.finAll();
	}
	
	@GetMapping("/empleados/{id}")
	public ResponseEntity<?> findEmpleadoById(@PathVariable Long id) {
		
		Empleado empleado = null;
		Map<String,Object> response = new HashMap<>();
		
		try	{
			empleado = empleadoService.findById(id);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(empleado == null) {
			response.put("mensaje", "El empleado con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}
	
	@PostMapping("/empleado")
	public ResponseEntity<?> saveJefe(@RequestBody Empleado empleado)	{
		
		Empleado empleadoNuevo = null;
		Map<String,Object> response = new HashMap<>();
		
		try	{
			empleadoNuevo = empleadoService.save(empleado);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar una insert a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El empleado ha sido creada con éxito");
		response.put("empleado", empleadoNuevo);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/empleados/{id}")
	public ResponseEntity<?> updateEmpleado(@RequestBody Empleado empleado, @PathVariable Long id) {
		
		Empleado empleadoActual = empleadoService.findById(id);	
		Map<String,Object> response = new HashMap<>();
		
		if(empleadoActual == null) {
			response.put("mensaje", "No se puede editar el empleado, el ID ".concat(id.toString().concat(" no existe en la base de datos")));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {		
			empleadoActual.setNombre(empleado.getNombre());
			empleadoActual.setDni(empleado.getDni());
			empleadoActual.setDepartamento(empleado.getDepartamento());
			empleadoActual.setSalario(empleado.getSalario());
			empleadoActual.setTelefono(empleado.getTelefono());
			
			empleadoService.save(empleadoActual);
			
		} catch(DataAccessException e){			
			response.put("mensaje", "Error al realizar un update a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El empleado ha sido actualizado con éxito");
		response.put("empleado", empleadoActual);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<?> deleteEmpleado(@PathVariable Long id) {
		
		Empleado empleadoEliminado = empleadoService.findById(id);	
		Map<String,Object> response = new HashMap<>();
		
		if(empleadoEliminado == null) {
			response.put("mensaje", "No se puede eliminar el empleado, el ID ".concat(id.toString()).concat(" no existe en la base de datos"));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			
		}
		
		try {
			empleadoService.delete(id);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar un delete a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El empleado ha sido eliminado con éxito");
		response.put("empleado", empleadoEliminado);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}

}
