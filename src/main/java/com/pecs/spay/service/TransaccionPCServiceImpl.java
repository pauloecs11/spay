package com.pecs.spay.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecs.spay.model.TransaccionPC;
import com.pecs.spay.repository.TransaccionPCRepository;

@Service
public class TransaccionPCServiceImpl implements TransaccionPCService{

	@Autowired
	TransaccionPCRepository transaccionPCRepository;
	
	@Override
	public TransaccionPC save(TransaccionPC transaccionPC) {
		return transaccionPCRepository.save(transaccionPC);
	}

	@Override
	public Optional<TransaccionPC> get(Integer id) {
		return transaccionPCRepository.findById(id);
	}

	//Aqui podria poner una busqueda por token. O que diga si encuentra el token. true o false.
	@Override
	public boolean existByToken(String token) {
		return transaccionPCRepository.existsByToken(token);
	}
	
	
	@Override
	public void update(TransaccionPC transacPc) {
		transaccionPCRepository.save(transacPc);
		
	}

	@Override
	public void delete(Integer id) {
		transaccionPCRepository.deleteById(id);
		
	}

	@Override
	public List<TransaccionPC> findAll() {
		return transaccionPCRepository.findAll();
	}



	

}
