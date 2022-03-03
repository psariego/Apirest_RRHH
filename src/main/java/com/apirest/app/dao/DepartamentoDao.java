package com.apirest.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apirest.app.entity.Departamento;
import com.apirest.app.entity.Empleado;
import com.apirest.app.entity.Jefe;

@Repository
public interface DepartamentoDao extends CrudRepository<Departamento, Long>{

	@Query("from Empleado")
	public List<Empleado> findAllEmpleados();
	
	@Query("from Jefe")
	public List<Jefe> findAllJefes();
}
