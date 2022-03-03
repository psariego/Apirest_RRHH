package com.apirest.app.service;

import java.util.List;

import com.apirest.app.entity.Empleado;

public interface EmpleadoService {
	
	public List<Empleado> finAll();
	
	public Empleado findById(Long id);
	
	public Empleado save(Empleado empleado);
	
	public void delete(Long id);

}
