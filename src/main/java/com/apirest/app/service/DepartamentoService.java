package com.apirest.app.service;

import java.util.List;

import com.apirest.app.entity.Departamento;
import com.apirest.app.entity.Empleado;
import com.apirest.app.entity.Jefe;

public interface DepartamentoService {

	public List<Departamento> findAll();
	public Departamento findById(Long id);
	public Departamento save(Departamento departamento);
	public void delete(Long id);
	
	public List<Empleado> findAllEmpleados();
	public List<Jefe> findAllJefes();
}
