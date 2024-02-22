package com.jipe.vivaupe.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.jipe.vivaupe.dto.EventoDTO;
import com.jipe.vivaupe.util.HttpRequestUtil;
import com.jipe.vivaupe.util.Util;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HandlerTeste implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("TesteIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        RequestEnvelope envelope = input.getRequestEnvelope();

        Map<String, Slot> slots = Util.pegarSlots(envelope);
        String data = slots.get("data").getValue();

        String dataFormatada = Util.converterData(data);
        List<EventoDTO> eventos = HttpRequestUtil.fazerRequisicao(dataFormatada);

        String speechText = "Eventos: ";

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("HelloWorld", speechText)
                .build();
    }

}
