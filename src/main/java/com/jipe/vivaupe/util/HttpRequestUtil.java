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
import java.util.logging.Level;
import java.util.logging.Logger;

@UtilityClass
public class HttpRequestUtil {

    private static final String API_KEY = System.getenv("API_KEY");
    private static final Logger logger = Logger.getLogger(HttpRequestUtil.class.getName());
    private static final String URL = "https://www.googleapis.com/calendar/v3/calendars/" +
            "c_b1beca7a002c4b4065fd6fd4b34d45c0cdd2248a0dd075be338604d565f4c506@group.calendar.google.com/" +
            "events?orderBy=startTime&singleEvents=true&key=" + API_KEY;
    private HttpGet request = new HttpGet();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public List<EventoDTO> fazerRequisicao(String parametroDePesquisa) {
        request.setURI(URI.create(URL + parametroDePesquisa));
        request.addHeader("accept", "application/json");

        try {
            HttpEntity resposta = httpClient.execute(request).getEntity();
            String resultado = EntityUtils.toString(resposta);

            List<EventoDTO> eventos = Util.converterParaDto(resultado);

            return eventos;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Ocorreu um erro no método fazerRequisicao().",e);
        }

        return Collections.emptyList();
    }

}
