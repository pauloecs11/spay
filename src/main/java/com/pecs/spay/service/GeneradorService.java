package com.pecs.spay.service;

import java.util.List;
import java.util.Optional;

import com.pecs.spay.model.Generador;

public interface GeneradorService {
	
	
	public Generador save(Generador generador);
	public Optional<Generador> get(Integer id);
	public void update(Generador generador);
	public void delete(Integer id);
	public List<Generador> findAll();


}
