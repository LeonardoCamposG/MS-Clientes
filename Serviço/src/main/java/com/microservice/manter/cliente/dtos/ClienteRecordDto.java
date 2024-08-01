package com.microservice.manter.cliente.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRecordDto(@NotBlank String nome, @NotNull Integer idade, @NotBlank String cidade, @NotBlank String estado, @NotBlank String rep) {

}
