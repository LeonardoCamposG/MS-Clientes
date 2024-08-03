package com.microservice.manter.cliente.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservice.manter.cliente.entities.ClienteModel;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, UUID>{

	@Query("Select c From ClienteModel c Where Lower(c.estado) = Lower(:estado)")
	Optional<List<ClienteModel>> findAllByEstado(@Param("estado") String estado);
	
	@Query("Select c From ClienteModel c Where Lower(c.rep) = Lower(:rep)")
	Optional<List<ClienteModel>> findAllByRepresentante(@Param("rep") String rep);
	
	@Query("Select c From ClienteModel c Where c.nome like Concat('%', :nome, '%')")
	Optional<List<ClienteModel>> findAllByNome(@Param("nome") String nome);
}