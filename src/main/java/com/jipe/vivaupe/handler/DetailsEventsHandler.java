package com.jipe.vivaupe.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.jipe.vivaupe.service.EventoService;
import com.jipe.vivaupe.util.Util;

import java.util.Map;
import java.util.Optional;

public class DetailsEventsHandler implements RequestHandler {

    EventoService service = new EventoService();
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("DetailsEventsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        RequestEnvelope envelope = input.getRequestEnvelope();

        Map<String, Slot> slots = Util.pegarSlots(envelope);
        String evento = slots.get("evento").getValue();

        String speechText = service.detailingEvents(evento);

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("HelloWorld", speechText)
                .withReprompt(speechText)
                .build();
    }
}
