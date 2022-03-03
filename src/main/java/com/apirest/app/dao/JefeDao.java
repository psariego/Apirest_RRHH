package com.apirest.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apirest.app.entity.Jefe;

@Repository
public interface JefeDao extends CrudRepository<Jefe, Long>{

}
