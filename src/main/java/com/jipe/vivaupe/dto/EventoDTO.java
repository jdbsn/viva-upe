package com.jipe.vivaupe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EventoDTO {

    @JsonProperty("summary")
    private String nome;

    @JsonProperty("start")
    private InicioDTO hora;

    @JsonProperty("description")
    private String descricao;

}
