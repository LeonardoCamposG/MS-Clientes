package com.microservice.manter.cliente.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteRecordDto(@NotBlank String nome, @NotNull Integer idade, @NotBlank String cidade, @NotBlank @Size(max = 2)  String estado, @NotBlank String rep) {

}
