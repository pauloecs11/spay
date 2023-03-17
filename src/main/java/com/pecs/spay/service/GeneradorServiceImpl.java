package com.pecs.spay.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecs.spay.model.Generador;
import com.pecs.spay.repository.GeneradorRepository;

@Service
public class GeneradorServiceImpl implements GeneradorService {

	@Autowired
	GeneradorRepository generadorRepository;
	
	
	@Override
	public Generador save(Generador generador) {
		return generadorRepository.save(generador);
	}

	@Override
	public Optional<Generador> get(Integer id) {
		return generadorRepository.findById(id);
	}

	@Override
	public void update(Generador generador) {
		generadorRepository.save(generador);
		
	}

	@Override
	public void delete(Integer id) {
		generadorRepository.deleteById(id);
	}

	@Override
	public List<Generador> findAll() {
		return generadorRepository.findAll();
	}

}
