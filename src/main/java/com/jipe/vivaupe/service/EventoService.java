package com.jipe.vivaupe.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jipe.vivaupe.dto.EventoDTO;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EventoService {

    private static final String URL = "https://www.googleapis.com/calendar/v3/calendars/c_b1beca7a002c4b4065fd6fd4b34d45c0cdd2248a0dd075be338604d565f4c506@group.calendar.google.com/events?key=AIzaSyB1qRSThLWj1gdX3KaI7pPzdNwHZCre3bA&maxResults=1";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpGet request = new HttpGet();

    public EventoDTO fazerRequisicao() {
        request.setURI(URI.create(URL));
        request.addHeader("accept", "application/json");

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);

            System.out.println(result);

            String nomeEvento = jsonNode.get("items").get(0).get("summary").asText();
            String dataString = jsonNode.get("items").get(0).get("start").get("dateTime").asText();

            OffsetDateTime data = OffsetDateTime.parse(dataString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            return new EventoDTO();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EventoDTO();
    }

}
