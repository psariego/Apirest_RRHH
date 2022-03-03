package com.apirest.app.service;

import java.util.List;

import com.apirest.app.entity.Jefe;


public interface JefeService {

	public List<Jefe> finAll();
	
	public Jefe findById(Long id);
	
	public Jefe save(Jefe jefe);
	
	public void delete(Long id);
}
