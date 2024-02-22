package com.jipe.vivaupe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InicioDTO {

    @JsonProperty("dateTime")
    private String hora;

    @JsonProperty("timeZone")
    private String fuso;


}
