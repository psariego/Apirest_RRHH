package com.apirest.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apirest.app.dao.UsuarioDao;
import com.apirest.app.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> finAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuario saveUsuario(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}
	
	

}
