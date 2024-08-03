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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Operation(summary = "Busca todos os clientes", description = "Retorna uma lista de todos os clientes")
	@GetMapping()
	public ResponseEntity<List<ClienteModel>> buscarTodosClientes(){
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarTodosClientes());
	}
	
	@Operation(summary = "Busca um cliente por ID", description = "Retorna um cliente específico pelo ID")
	@ApiResponses(value = {
	       @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
	       @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	   })
	@GetMapping("/id/{id}")
	public ResponseEntity<Object> buscarClientePorId(@PathVariable(value = "id") UUID id){
		Optional<ClienteModel> cliente = clienteService.buscarClientePorId(id);
		if(cliente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
	}
	
	@Operation(summary = "Busca clientes por estado", description = "Retorna uma lista de clientes pelo estado")
	   @ApiResponses(value = {
	       @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
	       @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado para o estado")
	   })
	@GetMapping("/estado/{estado}")
	public ResponseEntity<Object> buscarClientesPorEstado(@PathVariable(value = "estado") String estado){
		List<ClienteModel> clientes = clienteService.buscarClientesPorEstado(estado)
														      .orElseGet(Collections::emptyList);
		if(clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum cliente para esse estado!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	@Operation(summary = "Busca clientes por representante", description = "Retorna uma lista de clientes pelo representante")
	   @ApiResponses(value = {
	       @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
	       @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado para o representante")
	   })
	@GetMapping("/rep/{rep}")
	public ResponseEntity<Object> buscarClientesPorRepresentante(@PathVariable(value = "rep") String rep){
		List<ClienteModel> clientes = clienteService.buscarClientesPorRepresentante(rep)
													.orElseGet(Collections::emptyList);
		if(clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum cliente para esse representante!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	@Operation(summary = "Busca clientes por nome", description = "Retorna uma lista de clientes pelo nome")
	   @ApiResponses(value = {
	       @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
	       @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado com esse nome")
	   })
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Object> buscarClientesPorNome(@PathVariable(value = "nome") String nome){
		List<ClienteModel> clientes = clienteService.buscarClientesPorNome(nome)
													.orElseGet(Collections::emptyList);
		if(clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum cliente com esse nome!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	 
	@Operation(summary = "Cadastra um novo cliente", description = "Cria um novo cliente")
	@PostMapping()
	public ResponseEntity<ClienteModel> cadastrarCliente(@RequestBody @Valid ClienteRecordDto clienteRecordDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.inserirCliente(clienteRecordDto));
	}
	
	@Operation(summary = "Atualiza um cliente existente", description = "Atualiza os dados de um cliente existente")
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarCliente(@PathVariable(value = "id") UUID id,
												   @RequestBody @Valid ClienteRecordDto clienteRecordDto){
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizarCliente(id, clienteRecordDto));
	}
	
	@Operation(summary = "Deleta um cliente existente", description = "Remove um cliente existente pelo ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarCliente(@PathVariable(value = "id") UUID id){
		boolean test = clienteService.excluirClientePorId(id);
		if(!test) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso!"); 
	}
}
