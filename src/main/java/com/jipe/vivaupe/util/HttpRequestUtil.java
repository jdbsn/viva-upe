package com.jipe.vivaupe.util;

import com.jipe.vivaupe.dto.EventoDTO;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class HttpRequestUtil {

    private static final String URL = "https://www.googleapis.com/calendar/v3/calendars/" +
            "c_b1beca7a002c4b4065fd6fd4b34d45c0cdd2248a0dd075be338604d565f4c506@group.calendar.google.com/" +
            "events?key=AIzaSyB1qRSThLWj1gdX3KaI7pPzdNwHZCre3bA&maxResults=4&timeMin=";
    private HttpGet request = new HttpGet();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public List<EventoDTO> fazerRequisicao(String data) {
        request.setURI(URI.create(URL + data));
        request.addHeader("accept", "application/json");

        try {
            HttpEntity resposta = httpClient.execute(request).getEntity();
            String resultado = EntityUtils.toString(resposta);

            List<EventoDTO> eventos = Util.converterParaDto(resultado);

            System.out.println(eventos.toString());

            return eventos;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

}
