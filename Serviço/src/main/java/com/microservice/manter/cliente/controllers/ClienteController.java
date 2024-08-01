package com.microservice.manter.cliente.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.manter.cliente.dtos.ClienteRecordDto;
import com.microservice.manter.cliente.entities.ClienteModel;
import com.microservice.manter.cliente.repositories.ClienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepository;
	
	@PostMapping()
	public ResponseEntity<ClienteModel> cadastrarCliente(@RequestBody @Valid ClienteRecordDto clienteRecordDto){
		ClienteModel cliente = new ClienteModel();
		BeanUtils.copyProperties(clienteRecordDto, cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
	}
	
	@GetMapping()
	public ResponseEntity<List<ClienteModel>> buscarTodosClientes(){
		return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarClientePorId(@PathVariable(value = "id") UUID id){
		Optional<ClienteModel> cliente = clienteRepository.findById(id);
		if(cliente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarCliente(@PathVariable(value = "id") UUID id,
												   @RequestBody @Valid ClienteRecordDto clienteRecordDto){
		Optional<ClienteModel> cliente = clienteRepository.findById(id);
		if(cliente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		ClienteModel clienteM = cliente.get();
		BeanUtils.copyProperties(clienteRecordDto, clienteM);
		return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.save(clienteM));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarCliente(@PathVariable(value = "id") UUID id){
		Optional<ClienteModel> cliente = clienteRepository.findById(id);
		if(cliente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		clienteRepository.delete(cliente.get());
		return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso!"); 
	}
}
