package com.apirest.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apirest.app.dao.DepartamentoDao;
import com.apirest.app.entity.Departamento;
import com.apirest.app.entity.Empleado;
import com.apirest.app.entity.Jefe;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

	@Autowired
	DepartamentoDao departamentoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Departamento> findAll() {
		return (List<Departamento>) departamentoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Departamento findById(Long id) {
		return departamentoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Departamento save(Departamento departamento) {
		return departamentoDao.save(departamento);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		departamentoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAllEmpleados() {
		return departamentoDao.findAllEmpleados();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Jefe> findAllJefes() {
		return departamentoDao.findAllJefes();
	}

}
