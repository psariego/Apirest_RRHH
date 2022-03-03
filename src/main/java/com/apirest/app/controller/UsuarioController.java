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

import com.apirest.app.dao.UsuarioDao;
import com.apirest.app.entity.Usuario;
import com.apirest.app.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/usuarios")
	public List<Usuario> index(){
		return usuarioService.finAll();
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> findUsuarioById(@PathVariable Long id) {
		
		Usuario usuario = null;
		Map<String,Object> response = new HashMap<>();
		
		try	{
			usuario = usuarioService.findById(id);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(usuario == null) {
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario)	{
		
		Usuario usuarioNuevo = null;
		Map<String,Object> response = new HashMap<>();
		
		try	{
			usuarioNuevo = usuarioService.saveUsuario(usuario);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar una insert a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El usuario ha sido creada con éxito");
		response.put("usuario", usuarioNuevo);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
		
		Usuario usuarioActual = usuarioService.findById(id);	
		Map<String,Object> response = new HashMap<>();
		
		if(usuarioActual == null) {
			response.put("mensaje", "No se puede editar el usuario, el ID ".concat(id.toString().concat(" no existe en la base de datos")));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {		
			usuarioActual.setPass(usuario.getPass());
			usuarioActual.setUsuario(usuario.getUsuario());
			
			usuarioService.saveUsuario(usuarioActual);
			
		} catch(DataAccessException e){			
			response.put("mensaje", "Error al realizar un update a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "El usuario ha sido actualizado con éxito");
		response.put("usuario", usuarioActual);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		
		Usuario usuarioEliminado = usuarioService.findById(id);	
		Map<String,Object> response = new HashMap<>();
		
		if(usuarioEliminado == null) {
			response.put("mensaje", "No se puede eliminar el usuario, el ID ".concat(id.toString()).concat(" no existe en la base de datos"));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			
		}
		
		try {
			usuarioService.delete(id);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar un delete a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario ha sido eliminado con éxito");
		response.put("usuario", usuarioEliminado);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}
