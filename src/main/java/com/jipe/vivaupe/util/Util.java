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
import java.util.logging.Level;
import java.util.logging.Logger;

@UtilityClass
public class Util {

    private static final Logger logger = Logger.getLogger(Util.class.getName());

    public Map<String, Slot> pegarSlots(RequestEnvelope envelope) {
        if (envelope.getRequest() instanceof IntentRequest intentRequest) {
            return intentRequest.getIntent().getSlots();
        }
        return Collections.emptyMap();
    }

    public String converterData(String data) {
        String horaMinuto = "";

        try {
            LocalDateTime atual = LocalDateTime.now();
            LocalDateTime dataFornecida = LocalDateTime.parse(data + "T00:00:00",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

            if(dataFornecida.isAfter(atual)) {
                horaMinuto = "00:00";
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                horaMinuto = atual.format(formatter);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Ocorreu um erro no método converterData().", e);
        }

        return data + "T" + horaMinuto + ":00-03:00";
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
            logger.log(Level.WARNING, "Ocorreu um erro no método converterParaDto().",e);
        }

        return Collections.emptyList();
    }

}
