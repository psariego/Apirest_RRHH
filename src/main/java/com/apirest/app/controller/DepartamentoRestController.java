package com.apirest.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.app.entity.Departamento;
import com.apirest.app.service.DepartamentoService;

@RestController
@RequestMapping("/api")
public class DepartamentoRestController {
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping("/departamentos")
	public List<Departamento> index(){
		return departamentoService.findAll();
	}

}
