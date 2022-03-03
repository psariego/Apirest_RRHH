package com.apirest.app.service;

import java.util.List;

import com.apirest.app.entity.Usuario;

public interface UsuarioService {
	
	public List<Usuario> finAll();
	
	public Usuario findById(Long id);
	
	public Usuario saveUsuario(Usuario usuario);
	
	public void delete(Long id);

}
