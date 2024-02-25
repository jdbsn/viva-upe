package com.jipe.vivaupe.util;

import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Slot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jipe.vivaupe.dto.EventoDTO;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@UtilityClass
public class Util {

    public Map<String, Slot> pegarSlots(RequestEnvelope envelope) {
        if (envelope.getRequest() instanceof IntentRequest intentRequest) {
            return intentRequest.getIntent().getSlots();
        }
        return Collections.emptyMap();
    }

    public String converterData(String data) {
        try {
            LocalDateTime atual = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String horaMinuto = atual.format(formatter);

            String horaFormatada = data + "T" + horaMinuto + ":00" + "-03:00";

            System.out.println(horaFormatada);

            return horaFormatada;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Como o queryParam pode ser vazio, não tem problema.
        return "";
    }

    public List<EventoDTO> converterParaDto(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<EventoDTO> eventos;
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode itemsNode = rootNode.get("items");

            if(itemsNode == null) {
                return Collections.emptyList();
            }

            eventos = objectMapper.readValue(itemsNode.toString(), new TypeReference<>() {});

            return eventos;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}