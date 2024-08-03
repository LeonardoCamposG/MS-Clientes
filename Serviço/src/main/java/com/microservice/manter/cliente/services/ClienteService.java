package com.microservice.manter.cliente.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.manter.cliente.dtos.ClienteRecordDto;
import com.microservice.manter.cliente.entities.ClienteModel;
import com.microservice.manter.cliente.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<ClienteModel> buscarTodosClientes(){
		return clienteRepository.findAll();
	}
	
	public Optional<ClienteModel> buscarClientePorId(UUID id) {
		Optional<ClienteModel> cliente = clienteRepository.findById(id);
		return cliente;
	}
	
	public Optional<List<ClienteModel>> buscarClientesPorEstado(String estado){
		return clienteRepository.findAllByEstado(estado);
	}
	
	public Optional<List<ClienteModel>> buscarClientesPorRepresentante(String rep){
		return clienteRepository.findAllByRepresentante(rep);
	}
	
	public Optional<List<ClienteModel>> buscarClientesPorNome(String nome){
		return clienteRepository.findAllByNome(nome);
	}
	
	public ClienteModel inserirCliente(ClienteRecordDto clienteRecordDto) {
		ClienteModel cliente = new ClienteModel();
		BeanUtils.copyProperties(clienteRecordDto, cliente);
		return clienteRepository.save(cliente);
	}

	public ClienteModel atualizarCliente(UUID id, ClienteRecordDto clienteRecordDto) {
		Optional<ClienteModel> cliente = buscarClientePorId(id);
		ClienteModel clienteM = cliente.get();
		BeanUtils.copyProperties(clienteRecordDto, clienteM);
		return clienteRepository.save(clienteM);
	}
	
	public boolean excluirClientePorId(UUID id) {
		Optional<ClienteModel> cliente = buscarClientePorId(id);
		if(cliente.isEmpty()) {
			return false;
		}
		clienteRepository.delete(cliente.get());
		return true;
	}
}
