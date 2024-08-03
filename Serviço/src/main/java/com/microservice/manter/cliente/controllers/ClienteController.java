package com.microservice.manter.cliente.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.microservice.manter.cliente.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping()
	public ResponseEntity<List<ClienteModel>> buscarTodosClientes(){
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarTodosClientes());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Object> buscarClientePorId(@PathVariable(value = "id") UUID id){
		Optional<ClienteModel> cliente = clienteService.buscarClientePorId(id);
		if(cliente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
	}
	
	@GetMapping("/estado/{estado}")
	public ResponseEntity<Object> buscarClientesPorEstado(@PathVariable(value = "estado") String estado){
		List<ClienteModel> clientes = clienteService.buscarClientesPorEstado(estado)
														      .orElseGet(Collections::emptyList);
		if(clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum cliente para esse estado!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	@GetMapping("/rep/{rep}")
	public ResponseEntity<Object> buscarClientesPorRepresentante(@PathVariable(value = "rep") String rep){
		List<ClienteModel> clientes = clienteService.buscarClientesPorRepresentante(rep)
													.orElseGet(Collections::emptyList);
		if(clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum cliente para esse representante!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Object> buscarClientesPorNome(@PathVariable(value = "nome") String nome){
		List<ClienteModel> clientes = clienteService.buscarClientesPorNome(nome)
													.orElseGet(Collections::emptyList);
		if(clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum cliente com esse nome!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	@PostMapping()
	public ResponseEntity<ClienteModel> cadastrarCliente(@RequestBody @Valid ClienteRecordDto clienteRecordDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.inserirCliente(clienteRecordDto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarCliente(@PathVariable(value = "id") UUID id,
												   @RequestBody @Valid ClienteRecordDto clienteRecordDto){
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizarCliente(id, clienteRecordDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarCliente(@PathVariable(value = "id") UUID id){
		boolean test = clienteService.excluirClientePorId(id);
		if(!test) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso!"); 
	}
}
