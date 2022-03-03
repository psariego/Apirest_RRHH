package com.apirest.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apirest.app.entity.Usuario;

@Repository
public interface UsuarioDao extends CrudRepository<Usuario, Long>{

}
