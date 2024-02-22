package com.jipe.vivaupe.service;

import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Slot;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jipe.vivaupe.dto.EventoDTO;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EventoService {
    private static final String URL = "https://www.googleapis.com/calendar/v3/calendars/c_b1beca7a002c4b4065fd6fd4b34d45c0cdd2248a0dd075be338604d565f4c506@group.calendar.google.com/events?key=AIzaSyB1qRSThLWj1gdX3KaI7pPzdNwHZCre3bA&maxResults=4&timeMin=";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpGet request = new HttpGet();

    public EventoDTO fazerRequisicao(String data) {
        request.setURI(URI.create(URL + data));

        request.addHeader("accept", "application/json");

        try {
            HttpEntity resposta = httpClient.execute(request).getEntity();

            String resultado = EntityUtils.toString(resposta);

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(resultado);

            JsonNode itemsNode = rootNode.get("items");

            System.out.println(itemsNode.toString());

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<EventoDTO> evento = objectMapper.readValue(itemsNode.toString(),  new TypeReference<List<EventoDTO>>(){});

            System.out.println(evento.toString());

            evento.forEach(e -> System.out.println("Evento: " + e.getNome() + "Data: " + e.getHora().getHora()));

            return new EventoDTO();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EventoDTO();
    }

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

    /**
     * @param data
     * @return
     */
    public String existingEvents(String data){
        try {
            HttpEntity resposta = httpClient.execute(request).getEntity();

            String resultado = EntityUtils.toString(resposta);

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(resultado);

            JsonNode itemsNode = rootNode.get("items");

            System.out.println(itemsNode.toString());

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<EventoDTO> evento = objectMapper.readValue(itemsNode.toString(),  new TypeReference<List<EventoDTO>>(){});

            List<String> eventos = new ArrayList<>();

            evento.forEach(e -> eventos.add(e.getNome()));

            if (eventos.size() > 1) {
                return "Os eventos de " + data + " são " + eventos.toString();
            } else if (eventos.size() == 1) {
                return "O único evento de " + data + " é " + eventos.toString();
            } else{
                return "Não existem eventos na UPE em " + data;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ops, houve algum erro. Tente de novo";

    }


}
