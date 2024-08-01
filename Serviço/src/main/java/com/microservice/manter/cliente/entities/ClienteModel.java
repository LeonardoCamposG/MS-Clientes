package com.microservice.manter.cliente.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@CrossOrigin("*")
@Table(name = "tb_cliente")
public class ClienteModel implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID	id;
	private String	nome;
	private Integer idade;
	private String  cidade;
	private String  estado;
	private String  rep; // Representante.
	
	public ClienteModel() {
	}

	public ClienteModel(UUID id, String nome, Integer idade, String cidade, String estado, String rep) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.cidade = cidade;
		this.estado = estado;
		this.rep = rep;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteModel other = (ClienteModel) obj;
		return Objects.equals(id, other.id);
	}	
}
