package com.pecs.spay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.pecs.spay.model.TransaccionPC;

//@Repository
public interface TransaccionPCRepository extends JpaRepository<TransaccionPC, Integer>{
	
	boolean existsByToken(String token);
}
