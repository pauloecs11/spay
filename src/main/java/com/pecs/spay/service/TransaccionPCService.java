package com.pecs.spay.service;

import java.util.List;
import java.util.Optional;
import com.pecs.spay.model.TransaccionPC;

public interface TransaccionPCService {

	public TransaccionPC save(TransaccionPC transaccionPC);
	public Optional<TransaccionPC> get(Integer id);
	public void update(TransaccionPC transacPc);
	public void delete(Integer id);
	public List<TransaccionPC> findAll();
	public boolean existByToken(String token);
}
